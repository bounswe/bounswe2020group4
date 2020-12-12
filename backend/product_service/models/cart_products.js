const mongoose = require("mongoose");

/**
 * Schema of CartProduct object in the database. CartProduct documents reside
 * in 'cart_products' collection.
 */
module.exports.CartProduct = mongoose.model(
  "CartProduct",
  new mongoose.Schema({
    cartId: Number,
    customerId: Number,
    vendorId: Number,
    productId: Number,
    status: String,
  }),
  "cart_products"
);
