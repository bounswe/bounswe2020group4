const mongoose = require("mongoose");
const OrderedProduct = require("./models/ordered_product").OrderedProduct;

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

module.exports.addNotification = async function (params) {
  try {
    const orderedProduct = await OrderedProduct.findOne({ orderId: params.orderId });

    const notification = new Notification({
      userId:
        params.userType === "customer" ? orderedProduct.vendorId.toString() : orderedProduct.customerId.toString(),
      userType: params.userType === "customer" ? "vendor" : "customer",
      name: "Cancel order",
      startTime: new Date(),
      summary: `Your order ${params.orderId} is ${params.status.toLowerCase()} by the ${params.userType}`,
      actorType: params.userType,
      actorId:
        params.userType === "customer" ? orderedProduct.customerId.toString() : orderedProduct.vendorId.toString(),
      target: params.orderId,
    });

    await notification.save();
  } catch (err) {
    console.log(err);
  }
};

module.exports.Notification = Notification;
