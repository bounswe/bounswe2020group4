const like = require("../views/like");
const comment = require("../views/comment");

// Initializes the endpoints.
module.exports.initialize = (app) => {
  /**
   * Gets product id and customer id, add likes to the product
   * from that user.
   */
  app.post("/like", async (request, response) => {
    await like.like(request.query);

    response.respond(200, "OK");
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
    await comment.delete(request.query);

    response.respond(200, "OK");
  });
};
