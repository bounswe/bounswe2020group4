const Like = require("../models/like").Like;
const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;

module.exports.like = async (params) => {
  try {
    const isLiked = await Like.countDocuments({ customerId: params.customerId, productId: params.productId });

    if (isLiked) {
      await Like.deleteOne({ customerId: params.customerId, productId: params.productId });
    } else {
      await Like.create({ customerId: params.customerId, productId: params.productId });
    }

    return true;
  } catch (error) {
    return error;
  }
};

module.exports.getWishlist = async (customerId) => {
  try {
    let likedProductIds = (await Like.find({ customerId: customerId })).map((likeProductId) => likeProductId.productId);
    let products = await Product.find({ id: { $in: likedProductIds } });

    products = await Promise.all(
      products.map(async (product) => {
        product = product.toJSON();

        const vendor = await Vendor.findOne({ id: product.vendorId });

        product.vendor = {
          name: vendor.name,
          rating: vendor.rating,
        };

        delete product._id;
        delete product.vendorId;

        return product;
      })
    );

    return products;
  } catch (error) {
    console.log(error);
    return error;
  }
};
