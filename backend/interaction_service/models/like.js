const mongoose = require("mongoose");

/**
 * Schema of a Like object in the database, like documents resides
 * in 'likes' collection.
 */
module.exports.Like = mongoose.model(
  "Like",
  new mongoose.Schema({
    customerId: mongoose.Schema.Types.ObjectId,
    productId: mongoose.Schema.Types.ObjectId,
  }),
  "likes"
);
