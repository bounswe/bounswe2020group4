const Comment = require("../models/comment").Comment;
const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const ObjectId = require("mongoose").Types.ObjectId;

/**
 * Adds comment to given product, from given customer, with given text.
 *
 * @param {
 *  userId: String,
 *  productId: String,
 *  text: String
 * } params
 *
 * @returns {commentId | error}
 */
module.exports.add = async (params) => {
  try {
    const product = await Product.findById(ObjectId(params.productId));

    if (!product) {
      return {
        success: false,
        message: "Product not found.",
      };
    }

    const vendor = await Vendor.findById(product.vendorId);
    const comment = new Comment({
      userId: ObjectId(params.userId),
      productId: ObjectId(params.productId),
      rating: params.rating,
      text: params.comment,
    });

    if (params.rating > product.rating) {
      product.rating = (product.rating + Math.random() * (5 - product.rating)).toFixed(2);
    } else {
      product.rating = product.rating - Math.random() * (5 - product.rating).toFixed(2);
    }

    if (params.rating > vendor.rating) {
      vendor.rating = vendor.rating + Math.random() * (5 - vendor.rating).toFixed(2);
    } else {
      vendor.rating = vendor.rating - Math.random() * (5 - vendor.rating).toFixed(2);
    }

    await Promise.all([comment.save(), vendor.save(), product.save()]);

    return {
      success: true,
      id: comment._id.toString(),
    };
  } catch (error) {
    return { success: false, message: error.message || error };
  }
};

/**
 * Deletes the comment with given id.
 * @param {
 *  id: String
 * } params
 *
 * @returns {void | error}
 */
module.exports.delete = async (params) => {
  try {
    await Comment.deleteOne({ _id: ObjectId(params.id) });
  } catch (error) {
    return error;
  }
};
