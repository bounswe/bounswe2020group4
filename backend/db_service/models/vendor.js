const mongoose = require("mongoose");

module.exports.Vendor = mongoose.model(
  "Vendor",
  new mongoose.Schema({
    name: String,
    email: String,
    password: String,
    address: String,
    rating: Number,
    longitude: String,
    latitude: String,
    company: String,
    website: String,
  }),
  "vendors"
);
