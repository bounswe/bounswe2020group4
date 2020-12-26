const Comment = require("../models/comment").Comment;
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
    const comment = new Comment({
      userId: ObjectId(params.userId),
      productId: ObjectId(params.productId),
      text: params.comment,
    });

    await comment.save();

    return {
      id: comment._id.toString(),
    };
  } catch (error) {
    return error;
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
