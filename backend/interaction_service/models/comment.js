const mongoose = require("mongoose");

/**
 * Schema of a Vendor object in the database, vendor documents resides
 * in 'vendors' collection.
 */
module.exports.Comment = mongoose.model(
  "Comment",
  new mongoose.Schema({
    userId: Number,
    productId: Number,
    text: String,
  }),
  "comments"
);
