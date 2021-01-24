const verification = require("../views/verification");
const order = require("../views/order");
const { ErrorCode } = require("../constants/error");

module.exports.initialize = (app) => {
  app.post("/check-credit-card", async (request, response) => {
    const result = await verification.checkCreditCard(request.query);

    if (result.success) {
      response.respond(200, result.msg);
    } else {
      response.respond(406, result.msg);
    }
  });

  app.post("/order", async (request, response) => {
    const result = await order.checkoutOrder(request.query);

    if (result.success) {
      response.respond(200, "OK", result.data);
    } else {
      response.respond(400, result.msg);
    }
  });

  app.get("/order", async (request, response) => {
    const result = await order.getOrders(request.query);

    if (!result.success) {
      response.respond(ErrorCode(result.message), result.message);
    } else {
      let data = { orders: result.data.orders };
      if (request.query.userType === "vendor") {
        data.totalEarnings = result.data.totalEarnings;
      }
      response.respond(200, "OK", data);
    }
  });

  app.patch("/order", async (request, response) => {
    const result = await order.updateOrderStatus(request.query);

    if (!result.success) {
      response.respond(ErrorCode(result.message), result.message);
    } else {
      response.respond(200, "Order status is successfully changed.");
    }
  });
  app.patch("/order/product", async (request, response) => {
    const result = await order.updateProductStatus(request.query);

    if (!result.success) {
      response.respond(ErrorCode(result.message), result.message);
    } else {
      response.respond(200, "Product status is successfully changed.");
    }
  });
};
