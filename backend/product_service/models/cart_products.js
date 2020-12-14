const mongoose = require("mongoose");

/**
 * Schema of CartProduct object in the database. CartProduct documents reside
 * in 'cart_products' collection.
 */
module.exports.CartProduct = mongoose.model(
  "CartProduct",
  new mongoose.Schema({
    customerId: mongoose.Schema.Types.ObjectId,
    vendorId:  mongoose.Schema.Types.ObjectId,
    productId:  mongoose.Schema.Types.ObjectId,
  }),
  "cart_products"
);
