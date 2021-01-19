const account = require("../views/account");
const { ErrorCode } = require("../constants/error");
// Initialize the routes.
module.exports.initialize = (app) => {
  /**
   * Gets user id and user type and responds with user's account info.
   */
  app.get("/account", async (request, response) => {
    const result = await account.getAccountInfo(request.extractParams());

    if (result.success) {
      response.respond(200, "OK", { result: result.account });
    } else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });
  /**
   * Gets user id, user type, and some fields, updates the account
   * with that id.
   */
  app.post("/account", async (request, response) => {
    const result = await account.updateAccountInfo(request.extractParams());

    if (result.success) {
      response.respond(200, "OK");
    } else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });
  /**
   * Gets user id, user type, and new password, then changes the password
   * of the account with that id.
   */
  app.post("/account-change-password", async (request, response) => {
    const result = await account.changePassword(request.extractParams());
    if (result.success) {
      response.respond(200, "OK");
    } else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });
  /**
   * Gets user id, and new address then adds the address
   * of the account with that id.
   */
  app.post("/account/address", async (request, response) => {
    const result = await account.addAddress(request.extractParams());

    if (result.success) {
      response.respond(200, "OK", result.address);
    } else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });
  /**
   * Gets user id, and new address then updates the address
   * of the account with that address title.
   */
  app.patch("/account/address", async (request, response) => {
    const result = await account.updateAddress(request.extractParams());

    if (result.success) {
      response.respond(200, "OK", result.address);
    } else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });
  /**
   * Gets user id, and new address then updates the address
   * of the account with that address title.
   */
  app.delete("/account/address", async (request, response) => {
    const result = await account.deleteAddress(request.extractParams());

    if (result.success) {
      response.respond(200, "OK", { address: result.address });
    } else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });
  /**
   * Gets email, password, user type and responds with status.
   */
  app.post("/login", async (request, response) => {
    const result = await account.login(request.extractParams());

    if (result.success) {
      response.respond(200, "OK", { userId: result.userId });
    } else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });
  /**
   * For customers: gets email, password and user type
   * For vendors: gets email, password, user type, location and company
   * Responds with status
   */
  app.post("/signup", async (request, response) => {
    const result = await account.signup(request.extractParams());

    if (result.success) {
      response.respond(200, "OK", { userId: result.userId });
    } else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });
};
