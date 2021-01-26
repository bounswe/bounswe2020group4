const mongoose = require("mongoose");

/**
 * Schema of a Product object in the database, product documents resides
 * in 'products' collection.
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
    sizes: [String],
    colors: [String],
    description: String,
    vendorId: mongoose.Schema.Types.ObjectId,
  }),
  "products"
);
