const account = require("../views/account");

// Initialize the routes.
module.exports.initialize = (app) => {
  /**
   * Gets user id and user type and responds with user's account info.
   */
  app.get("/account", async (request, response) => {
    const result = await account.getAccountInfo(request.query);
    if (result) {
      response.respond(200, "OK", {
        result,
      });
    } else {
      response.respond(404, "Account not found");
    }
  });

  /**
   * Gets email, password, user type and responds with status.
   */
  app.post("/login", async (request, response) => {
    const result = await account.login(request.query);
    if (result) {
      response.respond(200, "OK", { userId: result });
    } else {
      response.respond(404, "User not found");
    }
  });

  /**
   * For customers: gets email, password and user type
   * For vendors: gets email, password, user type, location and company
   * Responds with status
   */
  app.post("/signup", async (request, response) => {
    const result = await account.signup(request.query);
    let data = { userId: result };
    if (result == "This email has been already used") {
      data = { info: result };
    }
    response.respond(200, "OK", data);
  });
};
