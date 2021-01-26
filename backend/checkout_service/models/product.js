const mongoose = require("mongoose");

/**
 * Schema of a Vendor object in the database, vendor documents resides
 * in 'vendors' collection.
 */
module.exports.Product = mongoose.model(
  "Product",
  new mongoose.Schema({
    name: String,
    imageUrl: String,
    category: [String],
    rating: Number,
    price: Number,
    originalPrice: Number,
    productInfos: String,
    brand: String,
    vendorId: mongoose.Schema.Types.ObjectId,
  }),
  "products"
);
