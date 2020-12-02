const mongoose = require("mongoose");

/**
 * Schema of a Vendor object in the database, vendor documents resides
 * in 'vendors' collection.
 */
module.exports.Product = mongoose.model(
  "Product",
  new mongoose.Schema({
    name: String,
    id: Number,
    imageUrl: String,
    category: [String],
    rating: Number,
    price: Number,
    originalPrice: Number,
    stockValue: Object,
    brand: String,
    sizes: [String],
    colors: [String],
    vendorId: Number,
  }),
  "products"
);
