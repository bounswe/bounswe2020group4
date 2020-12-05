const mongoose = require("mongoose");

/**
 * Schema of a Like object in the database, like documents resides
 * in 'wishlist' collection.
 */
module.exports.Like = mongoose.model(
  "Like",
  new mongoose.Schema({
    customerId: Number,
    productId: Number,
  }),
  "likes"
);
