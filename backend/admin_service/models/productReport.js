const mongoose = require("mongoose");

/**
 * Schema of a Comment object in the database, comment documents resides
 * in 'comments' collection.
 */
module.exports.ProductReport = mongoose.model(
  "ProductReport",
  new mongoose.Schema({
    productId: mongoose.Schema.Types.ObjectId,
    message: String,
  }),
  "productReports"
);
