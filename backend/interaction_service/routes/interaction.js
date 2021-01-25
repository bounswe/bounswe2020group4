const like = require("../views/like");
const comment = require("../views/comment");
const message = require("../views/message");
const notification = require("../views/notification");
const { ErrorCode } = require("../constants/error");

// Initializes the endpoints.
module.exports.initialize = (app) => {
  /**
   * Gets product id and customer id, add likes to the product
   * from that user.
   */
  app.post("/like", async (request, response) => {
    const result = await like.like(request.query);
    if (result.success) {
      response.respond(200, "OK");
    } else {
      response.respond(404, result.message);
    }
  });

  /**
   * Gets product id and customer id and comment text, adds
   * that comment to the product from the customer.
   */
  app.post("/comment", async (request, response) => {
    const result = await comment.add(request.query);

    if (result.success) {
      response.respond(200, "OK", { commentId: result.id });
    } else {
      response.respond(500, result.message);
    }
  });

  /**
   * Gets comment id and deletes that comment.
   */
  app.delete("/comment", async (request, response) => {
    const result = await comment.delete(request.query);
    if (result.success) {
      response.respond(200, "OK");
    } else {
      response.respond(404, result.message);
    }
  });

  /**
   * Gets all last messages of a user with other users.s
   */
  app.get("/messages/last", async (request, response) => {
    const result = await message.getLastMessages(request.query);

    if (result.success) {
      response.respond(200, "OK", { lastMessages: result.messages });
    } else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });

  /**
   * Gets messages between two users
   */
  app.get("/messages", async (request, response) => {
    const result = await message.getMessages(request.query);

    if (result.success) {
      response.respond(200, "OK", { messages: result.messages });
    } else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });

  /**
   * Sends a message
   */
  app.post("/message", async (request, response) => {
    const result = await message.send(request.body);

    if (result.success) {
      response.respond(200, "OK", { message: result.message });
    } else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });

  /**
   * Gets notifications between two users
   */
  app.get("/notifications", async (request, response) => {
    const result = await notification.getNotifications(request.query);

    if (result.success) {
      response.respond(200, "OK", { notifications: result.response });
    } else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });
};
