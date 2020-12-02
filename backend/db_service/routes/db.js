const db = require("../db/init");

module.exports.initialize = (app) => {
  app.post("/db/init", async (request, response) => {
    await db.initializeMockDB();

    response.respond(200, "OK");
  });
};
