const account = require("../views/account");

// Initialize the routes.
module.exports.initialize = (app) => {
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

  app.post("/account", async (request, response) => {
    const result = await account.updateAccountInfo(request.query);

    if (result) {
      response.respond(200, "OK");
    } else {
      response.respond(404, "Account not found");
    }
  });

  app.post("/login", async (request, response) => {
    const result = await account.login(request.query);
    if (result) {
      response.respond(200, "OK", { userId: result });
    } else {
      response.respond(404, "User not found");
    }
  });

  app.post("/signup", async (request, response) => {
    const result = await account.signup(request.query);
    let data = { userId: result };
    if (result == "This email has been already used") {
      data = { info: result };
    }
    response.respond(200, "OK", data);
  });
};
