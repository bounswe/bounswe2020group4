const mongoose = require("mongoose");
const Product = require("./product").Product;
const Vendor = require("./vendor").Vendor;
const Like = require("./like").Like;

/**
 * Schema of a Notification object in the database, notification documents resides
 * in 'notifications' collection.
 */
const Notification = mongoose.model(
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

Notification.prototype.add = async function (productId) {
  try {
    const product = await Product.findById(productId);
    const vendor = await Vendor.findById(product.vendorId);
    const likes = await Like.find({ productId: productId });
    const notifications = [];
    const vendorName = [vendor.name, vendor.surname].join(" ").trim();

    likes.forEach((like) => {
      notifications.push(
        new Notification({
          userId: like.userId,
          userType: "customer",
          name: "Discount",
          startTime: new Date(),
          summary: `${vendorName}'s ${product.name} price dropped from ${product.originalPrice} to ${product.price}`,
          actorType: "vendor",
          actorId: product.vendorId,
          target: productId,
        })
      );
    });

    await Notification.insertMany(notifications);
  } catch (err) {
    console.log(err);
  }
};

module.exports.Notification = Notification;
