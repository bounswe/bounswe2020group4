const mongoose = require("mongoose");

/**
 * Schema of a Vendor object in the database, vendor documents resides
 * in 'vendors' collection.
 */
let schema =  new mongoose.Schema({
  name: String,
  imageUrl: String,
  category: [String],
  rating: Number,
  price: Number,
  originalPrice: Number,
  productInfos: String,
  description: String,
  brand: String,
  vendorId: mongoose.Schema.Types.ObjectId,
});
schema.index({ name: 'text', category: 'text', description: 'text', brand: 'text' });

module.exports.Product = mongoose.model(
  "Product",
  schema,
  "products"
);
