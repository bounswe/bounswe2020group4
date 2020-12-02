const like = require("../views/like");

module.exports.initialize = (app) => {
  app.post("/like", async (request, response) => {
    await like.like(request.query);

    response.respond(200, "OK");
  });
};
