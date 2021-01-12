const socketIO = require("socket.io");
const Customer = require("../models/customer").Customer;
const Message = require("../models/message").Message;
const Vendor = require("../models/vendor").Vendor;
const Admin = require("../models/admin").Admin;
const { ErrorMessage, ErrorCode } = require("../constants/error");
const ObjectId = require("mongoose").Types.ObjectId;
const userInfo = {};
const redisAdapter = require("socket.io-redis");
const redisClient = require("redis").createClient("redis://cache");

module.exports.initialize = (app) => {
  const io = socketIO(app, {
    cors: {
      origin: "*",
    },
  });
  io.adapter(redisAdapter({ pubClient: redisClient, subClient: redisClient.duplicate() }));

  io.on("connection", (socket) => {
    socket.on("discover", async (payload, response) => {
      try {
        if (!payload.userType || !payload.id) {
          return response({ code: ErrorCode(ErrorMessage.MISSING_PARAMETER), message: ErrorMessage.MISSING_PARAMETER });
        }

        const User = {
          admin: Admin,
          customer: Customer,
          vendor: Vendor,
        }[payload.userType];
        const user = await User.findById(ObjectId(payload.id));

        if (!user) {
          return response({ code: ErrorCode(ErrorMessage.USER_NOT_FOUND), message: ErrorMessage.USER_NOT_FOUND });
        }

        userInfo[payload.id] = {
          socket: socket,
          userType: payload.userType,
        };

        response({ code: 200, message: "Success" });
      } catch (error) {
        response({ code: 500, message: error.message || error });
      }
    });

    socket.on("message", async (payload, response) => {
      try {
        const UserModel = {
          admin: Admin,
          customer: Customer,
          vendor: Vendor,
        };

        if (!payload.userType || !payload.id || !payload.withId || !payload.withType || !payload.message) {
          return response({ code: ErrorCode(ErrorMessage.MISSING_PARAMETER), message: ErrorMessage.MISSING_PARAMETER });
        }

        const User = UserModel[payload.userType];
        const WithUser = UserModel[payload.userType];
        const [user, withUser] = await Promise.all([
          User.findById(ObjectId(payload.id)),
          WithUser.findById(ObjectId(payload.withId)),
        ]);

        if (!user || !withUser) {
          return response({ code: ErrorCode(ErrorMessage.USER_NOT_FOUND), message: ErrorMessage.USER_NOT_FOUND });
        }

        const message = new Message({
          userId: payload.id,
          withId: payload.withId,
          userType: payload.userType,
          withType: payload.withType,
          message: payload.message,
          date: new Date(),
        });

        await message.save();

        userInfo[payload.id] = {
          socket: socket,
          userType: payload.userType,
        };

        response({ code: 200, message: "Success" });
      } catch (err) {
        response({ code: 500, message: error.message || error });
      }
    });
  });
};
