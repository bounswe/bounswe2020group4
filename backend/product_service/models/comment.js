const mongoose = require("mongoose");

/**
 * Schema of a Comment object in the database, comment documents resides
 * in 'comments' collection.
 */
module.exports.Comment = mongoose.model(
  "Comment",
  new mongoose.Schema({
    userId: mongoose.Schema.Types.ObjectId,
    productId: mongoose.Schema.Types.ObjectId,
    text: String,
  }),
  "comments"
);
