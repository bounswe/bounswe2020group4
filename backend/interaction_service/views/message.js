const Message = require("../models/message").Message;
const Vendor = require("../models/vendor").Vendor;
const Customer = require("../models/customer").Customer;
const Admin = require("../models/admin").Admin;
const ObjectId = require("mongoose").Types.ObjectId;
const { ErrorMessage } = require("../constants/error");

/**
 * Gets message list for a given user
 *
 * @param {
 *  userId: String,
 *  userType: customer | vendor | admin,
 *  withType: customer | vendor | admin,
 *  withId: String
 * } params
 *
 * @returns {messages: [] | error}
 */
module.exports.getMessages = async (params) => {
  try {
    if (!params.id || !params.userType || !params.withId || !params.withType) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }

    let messages = await Message.find({
      $or: [
        {
          userId: ObjectId(params.id),
          userType: params.userType,
          withId: ObjectId(params.withId),
          withType: params.withType,
        },
        {
          userId: ObjectId(params.withId),
          userType: params.withType,
          withId: ObjectId(params.id),
          withType: params.withType,
        },
      ],
    }).sort({
      date: "descending",
    });
    const UserModel = {
      customer: Customer,
      vendor: Vendor,
      admin: Admin,
    };
    const users = {};
    [users[params.id], users[params.withId]] = await Promise.all([
      UserModel[params.userType].findById(ObjectId(params.id)),
      UserModel[params.withType].findById(ObjectId(params.withId)),
    ]);

    if (!users[params.withId] || !users[params.id]) {
      return { success: false, message: ErrorMessage.USER_NOT_FOUND };
    }

    messages = messages.map((message) => message.toJSON());
    Object.keys(users).forEach((userId) => {
      const user = users[userId].toJSON();

      users[userId] = {
        id: userId,
        name: [user.name, user.surname].join(" ").trim(),
        userType: userId === params.userId ? params.userType : params.withType,
      };
    });
    messages.map((message) => {
      message.id = message._id;
      message.user = users[message.userId.toString()];

      delete message.userId;
      delete message.userType;
      delete message.__v;
      delete message._id;
      delete message.withId;
      delete message.withType;
    });

    return {
      success: true,
      messages: messages,
    };
  } catch (error) {
    return { success: false, message: error.message || error };
  }
};

/**
 * Gets message list for a given user
 *
 * @param {
 *  userId: String,
 *  userType: customer | vendor | admin,
 * } params
 *
 * @returns {messages: [] | error}
 */
module.exports.getLastMessages = async (params) => {
  try {
    if (!params.id || !params.userType) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }

    const messages = await Message.find({
      $or: [
        { userId: ObjectId(params.id), userType: params.userType },
        { withId: ObjectId(params.id), withType: params.userType },
      ],
    }).sort({
      date: "descending",
    });
    const UserModel = {
      customer: Customer,
      vendor: Vendor,
      admin: Admin,
    };
    const lastMessages = {};
    const messagesResponse = [];

    messages.forEach((message) => {
      lastMessages[message.withId] = message.toJSON();
    });

    await Promise.all(
      Object.values(lastMessages).map(async (lastMessage) => {
        const [withType, withId] =
          lastMessage.userId.toString() === params.id
            ? [lastMessage.withType, lastMessage.withId]
            : [lastMessage.userType, lastMessage.userId];
        const withUser = (await UserModel[withType].findById(ObjectId(withId))).toJSON();

        messagesResponse.push({
          user: {
            name: [withUser.name, withUser.surname].join(" ").trim(),
            userType: withType,
            id: withId,
          },
          lastMessage: lastMessage.message,
          date: lastMessage.date,
        });
      })
    );

    messagesResponse.sort((a, b) => b.date - a.date);

    return {
      success: true,
      messages: messagesResponse,
    };
  } catch (error) {
    return { success: false, message: error.message || error };
  }
};
