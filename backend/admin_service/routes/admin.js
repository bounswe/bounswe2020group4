const report = require("../views/report");
const ban = require("../views/ban");

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


  app.post("/admin/vendor/changeStatus/:vendorId", async (request, response) => {

    if(!("status" in request.body)){
      response.respond(500,  "Please check your parameters. There should be status information related with current user.");
    }

    parameters = {}
    
    parameters["status"] = request.body.status;
    parameters["vendorId"] = request.params.vendorId;

    
    const result = await ban.changeStatusForVendor(parameters);

    if (result) {
      response.respond(200, "The vendor status is changed" );
    } else {
      response.respond(500, "Please check your parameters");
    }
  });






  app.post("/admin/customer/changeStatus/:customerId", async (request, response) => {

    if(!("status" in request.body)){
      response.respond(500,  "Please check your parameters. There should be status information related with current user.");
    }

    parameters = {}
    parameters["status"] = request.body.status;
    parameters["customerId"] = request.params.customerId;

    
    const result = await ban.changeStatusForCustomer(parameters);

    if (result) {
      response.respond(200, "The customer status is changed" );
    } else {
      response.respond(500, "Please check your parameters");
    }
  });


};
