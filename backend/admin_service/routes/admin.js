const report = require("../views/report");

// Initializes the endpoints.
module.exports.initialize = (app) => {
  /**
   * Gets product id and customer id and comment text, adds
   * that comment to the product from the customer.
   */
  app.post("/report/product", async (request, response) => {
    const result = await report.reportProduct(request.query);

    if (result.success) {
      response.respond(200, "OK", { productReportId: result.id });
    } else {
      response.respond(500, result.message);
    }
  });

  /**
   * Gets product id and customer id and comment text, adds
   * that comment to the product from the customer.
   */
  app.post("/report/comment", async (request, response) => {
    const result = await report.reportComment(request.query);

    if (result.success) {
      response.respond(200, "OK", { commentReportId: result.id });
    } else {
      response.respond(500, result.message);
    }
  });
};
