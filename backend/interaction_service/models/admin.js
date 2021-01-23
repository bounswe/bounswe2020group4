const mongoose = require("mongoose");

/**
 * Schema of a Vendor object in the database, vendor documents resides
 * in 'vendors' collection.
 */
module.exports.Admin = mongoose.model(
  "Admin",
  new mongoose.Schema({
    name: String,
    email: String,
    password: String,
  }),
  "admins"
);
