const checkout = require("../views/customer");
const verification = require("../views/verification");
const order = require("../views/order");

module.exports.initialize = (app) => {
    app.get("/addresses", async (request, response) => {
        const addresses = await checkout.getCustomerAddresses();

        if (addresses) {
            response.respond(200, "OK", { 
                addresses,
            });
        } else {
            response.respond(404, "No customer is found.");
        }
    });

    app.post("/address-add", async (request, response) => {
        await checkout.addCustomerAddress(request.query);

        response.respond(200, "OK");
    });

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

        if (result) {
            response.respond(200, "OK", result);
        } else {
            response.respond(400, "No ordered products, or the userType is missing");
        }
    });

    app.patch("/order", async (params) => {
        const result = await order.updateOrderStatus(request.query);

        if (result) {
            response.respond(200, "OK");
        } else {
            response.respond(400, "Missing arguments");
        }
    });
};