const mongoose = require("mongoose");

module.exports.Comment = mongoose.model(
  "Comment",
  new mongoose.Schema({
    userId: mongoose.Schema.Types.ObjectId,
    productId: mongoose.Schema.Types.ObjectId,
    text: String,
  }),
  "comments"
);
