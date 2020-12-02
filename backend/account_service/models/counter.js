const mongoose = require("mongoose");

module.exports.Counter = mongoose.model(
  "Counter",
  new mongoose.Schema({
    customerCounter: Number,
    vendorCounter: Number,
    productCounter: Number,
  }),
  "counters"
);
