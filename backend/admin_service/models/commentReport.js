const mongoose = require("mongoose");

/**
 * Schema of a Comment object in the database, comment documents resides
 * in 'comments' collection.
 */
module.exports.CommentReport = mongoose.model(
  "CommentReport",
  new mongoose.Schema({
    commentId: mongoose.Schema.Types.ObjectId,
    message: String,
  }),
  "commentReports"
);
