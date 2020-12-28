const mongoose = require("mongoose");

/**
 * Schema of a Vendor object in the database, vendor documents resides
 * in 'vendors' collection.
 */
module.exports.Vendor = mongoose.model(
  "Vendor",
  new mongoose.Schema({
    name: String,
    email: String,
    password: String,
    rating: Number,
    longitude: String,
    company: String,
    latitude: String,
  }),
  "vendors"
);
