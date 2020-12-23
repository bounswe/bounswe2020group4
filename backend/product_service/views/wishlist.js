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

        product.vendor = {
          name: vendor.name,
          rating: vendor.rating,
        };
        product.id = product._id.toString();

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
