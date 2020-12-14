const mongoose = require("mongoose");

/**
 * Schema of an Order object in the database, order documents resides
 * in 'orders' collection.
 */
module.exports.Order = mongoose.model(
    "Order",
    new mongoose.Schema({
        id: String,
        date: { type: Date, default: Date.now },
        address: String,
        customerId: mongoose.Schema.Types.ObjectId,
        vendorId: mongoose.Schema.Types.ObjectId,
        productId: mongoose.Schema.Types.ObjectId,
        paymentType: String,
        status: { type: String, default: "Pending" },
    }),
    "order"
);