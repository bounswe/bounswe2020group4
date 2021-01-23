const mongoose = require("mongoose");

/**
 * Schema of a Customer object in the database, customer documents resides
 * in 'customers' collection.
 */
module.exports.Customer = mongoose.model(
  "Customer",
  new mongoose.Schema({
    name: String,
    email: String,
    rating: Number,
    address: [Object],
    password: String,
    gender: String,
    phoneNumber: String,
    googleToken: String
  }),
  "customer"
);


