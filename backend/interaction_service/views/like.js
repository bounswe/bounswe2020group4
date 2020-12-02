const Like = require("../models/like").Like;

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
