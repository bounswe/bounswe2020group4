const Like = require("../models/like").Like;
const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const ObjectId = require("mongoose").Types.ObjectId;

/**
 * Gets a customerId and returns that customer's wishlist.
 * @param {customerId: String} customerId
 *
 * @returns {[Product]}
 */
module.exports.getWishlist = async (customerId) => {
  try {
    let likedProductIds = (await Like.find({ customerId: ObjectId(customerId) })).map(
      (likeProductId) => likeProductId.productId
    );
    let products = await Product.find({ _id: { $in: likedProductIds } });

    products = await Promise.all(
      products.map(async (product) => {
        product = product.toJSON();

        const vendor = await Vendor.findOne({ _id: product.vendorId });
        2;
        return {
          vendor: {
            name: vendor.name,
            rating: vendor.rating,
            id: vendor._id.toString(),
          },
          id: product._id.toString(),
          category: product.category,
          description: product.description,
          name: product.name,
          price: product.price,
          originalPrice: product.originalPrice,
          imageUrl: product.imageUrl,
          rating: product.rating ? Number(parseFloat(product.rating).toFixed(2)) : 0,
          brand: product.brand,
          productInfos: JSON.parse(product.productInfos),
        };
      })
    );

    return products;
  } catch (error) {
    console.log(error);
    return error;
  }
};
