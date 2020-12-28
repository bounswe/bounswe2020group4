const db = require("../db/init");

// Initializes the endpoints.
module.exports.initialize = (app) => {
  /**
   * Adds mock documents to the database.
   */
  app.post("/db/init", async (request, response) => {
    await db.initializeMockDB();

    response.respond(200, "OK");
  });
};
