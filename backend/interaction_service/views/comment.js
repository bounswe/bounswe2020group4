const Comment = require("../models/comment").Comment;
const ObjectId = require("mongoose").Types.ObjectId;

module.exports.add = async (params) => {
  try {
    const comment = new Comment({
      userId: params.userId,
      productId: params.productId,
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

module.exports.delete = async (params) => {
  try {
    await Comment.deleteOne({ _id: ObjectId(params.id) });
  } catch (error) {
    return error;
  }
};
