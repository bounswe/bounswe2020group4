const Notification = require("../models/notification").Notification;
const Customer = require("../models/customer").Customer;
const Vendor = require("../models/vendor").Vendor;
const Admin = require("../models/admin").Admin;
const ObjectId = require("mongoose").Types.ObjectId;
const { ErrorMessage } = require("../constants/error");

/**
 * Returns notifications of a user
 *
 * @param {
 *  userId: String,
 *  userType: String
 * } params
 *
 * @returns {Array(Notifications)}
 */
module.exports.getNotifications = async (params) => {
  try {
    if (!params.userId || !params.userType) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }
    const notifications = await Notification.find({ userType: params.userType, userId: ObjectId(params.userId) }).sort(
      "descending"
    );
    console.log(await Notification.find(), params);

    const response = {
      "@context": "https://www.w3.org/ns/activitystreams",
      summary: "BUYO Notifications",
      type: "Collection",
      totalItems: notifications.length,
      items: [],
    };
    const UserModel = {
      customer: Customer,
      vendor: Vendor,
      admin: Admin,
    };

    await Promise.all(
      notifications.map(async (notification) => {
        try {
          notification = notification.toJSON();
          const actor = await UserModel[notification.actorType].findById(notification.actorId);
          const actorName =
            notification.actorType === "customer" ? actor.email : [actor.name, actor.surname].join(" ").trim();

          response.items.push({
            type: "Update",
            name: notification.name,
            startTime: notification.startTime,
            summary: notification.summary,
            actor: {
              type: notification.actorType === "customer" ? "Person" : "Organization",
              id: notification.actorId.toString(),
              name: actorName,
            },
            target: notification.target.toString(),
          });
        } catch (error) {}
      })
    );

    response.totalItems = response.items.length;

    return { success: true, response };
  } catch (error) {
    return { success: false, message: error.message || error };
  }
};
