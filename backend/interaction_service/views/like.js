const Like = require("../models/like").Like;
const ObjectId = require("mongoose").Types.ObjectId;

/**
 * If a like exists from given customer to the given product, removes it
 * If it's not, adds a like to given product from given customer.
 * @param {
 *  customerId: String,
 *  productId: String
 * } params
 *
 * @returns {true|error}
 */
module.exports.like = async (params) => {
  try {
    const isLiked = await Like.countDocuments({
      customerId: ObjectId(params.customerId),
      productId: ObjectId(params.productId),
    });

    if (isLiked) {
      await Like.deleteOne({ customerId: ObjectId(params.customerId), productId: ObjectId(params.productId) });
    } else {
      await Like.create({ customerId: ObjectId(params.customerId), productId: ObjectId(params.productId) });
    }

    return true;
  } catch (error) {
    return error;
  }
};
