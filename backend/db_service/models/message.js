const mongoose = require("mongoose");

/**
 * Schema of a Like object in the database, like documents resides
 * in 'likes' collection.
 */
module.exports.Message = mongoose.model(
  "Message",
  new mongoose.Schema({
    userId: mongoose.Schema.Types.ObjectId,
    userType: String,
    withId: mongoose.Schema.Types.ObjectId,
    withType: String,
    message: String,
    date: Date,
  }),
  "messages"
);
