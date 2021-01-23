const file = require("../views/file");
const vendor = require("../views/vendor");
const { ErrorCode } = require("../constants/error");


module.exports.initialize = (app) => {
  app.post("/vendor/products", async (request, response) => {
    const result = await vendor.addProducts(request.body);
    if (result["idList"]) {
      response.respond(200, "OK", {
        result
      });
    } else {
      response.respond(400,"Please check your products' information");
    }
  });

  app.post("/file", async (request, response) => {
    const urls = await file.upload(request.files);

    response.respond(200, "OK", { urls });
  });


  app.patch("/vendor/products/:vendorId", async (request, response) => {
    var productId = request.params.id
    var changeParameters = request.body;

    const result = await vendor.updateProduct(productId,changeParameters);

    if (!!result.name) {
      response.respond(200, "OK", {
        result,
      });
    } 
    else {
      response.respond(400, result);
    }
  });



  app.get("/vendor/products/:vendorId", async (request, response) => {
    parameters = request.body
    parameters["vendorId"]  = request.params.vendorId

    const result = await vendor.getProducts(parameters);
    if ( !!result.success ) {
      response.respond(ErrorCode(result.message), result.message);
    }
    else if (!!result.productList) {
      response.respond(200, "OK", {
        result,
      });
    } 
    else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });


  app.get("/vendor/vendorlist", async (request, response) => {

    const result = await vendor.getVendorList();
    response.respond(200, "OK", {
      result
    });   

  });

};
