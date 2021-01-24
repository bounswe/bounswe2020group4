const mongoose = require("mongoose");
/**
 * Schema of an Order object in the database, order documents resides
 * in 'orders' collection.
 */
module.exports.OrderedProduct = mongoose.model(
  "OrderedProduct",
  new mongoose.Schema({
    orderId: String,
    address: String,
    customerId: mongoose.Schema.Types.ObjectId,
    vendorId: mongoose.Schema.Types.ObjectId,
    productId: mongoose.Schema.Types.ObjectId,
    productInfo: String,
    date: Date,
    status: { type: String, default: "Pending" },
    shippingPrice: { type: Number, default: 9.99 },
  }),
  "orderedProducts"
);