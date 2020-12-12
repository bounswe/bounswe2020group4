const mongoose = require("mongoose");

/**
 * Schema of a Vendor object in the database, vendor documents resides
 * in 'vendors' collection.
 */
module.exports.Counter = mongoose.model(
  "Counter",
  new mongoose.Schema({
    customerCounter: Number,
    vendorCounter: Number,
    productCounter: Number,
    cartCounter: Number,
  }),
  "counters"
);
