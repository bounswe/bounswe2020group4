const like = require("../views/like");
const comment = require("../views/comment");

module.exports.initialize = (app) => {
  app.post("/like", async (request, response) => {
    await like.like(request.query);

    response.respond(200, "OK");
  });

  app.post("/comment", async (request, response) => {
    const result = await comment.add(request.query);

    if (result.id) {
      response.respond(200, "OK", { commentId: result.id });
    } else {
      response.respond(500, "Invalid request");
    }
  });

  app.delete("/comment", async (request, response) => {
    await comment.delete(request.query);

    response.respond(200, "OK");
  });
};
