const mongoose = require("mongoose");

module.exports.Comment = mongoose.model(
  "Comment",
  new mongoose.Schema({
    userId: Number,
    productId: Number,
    text: String,
  }),
  "comments"
);
