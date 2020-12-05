const mongoose = require("mongoose");

/**
 * Schema of a Vendor object in the database, vendor documents resides
 * in 'vendors' collection.
 */
module.exports.Customer = mongoose.model(
  "Customer",
  new mongoose.Schema({
    name: String,
    email: String,
    id: Number,
    rating: Number,
    address: String,
    password: String,
  }),
  "customer"
);
