const mongoose = require("mongoose");

/**
 * Schema of a Like object in the database, like documents resides
 * in 'likes' collection.
 */
module.exports.Notification = mongoose.model(
  "Notification",
  new mongoose.Schema({
    userId: mongoose.Schema.Types.ObjectId,
    userType: String,
    name: String,
    startTime: Date,
    summary: String,
    actorType: String,
    actorId: mongoose.Schema.Types.ObjectId,
    target: mongoose.Schema.Types.ObjectId,
  }),
  "notifications"
);
