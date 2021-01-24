const file = require("../views/file");
const vendor = require("../views/vendor");
const { ErrorCode } = require("../constants/error");

module.exports.initialize = (app) => {
  app.post("/vendor/products", async (request, response) => {
    const result = await vendor.addProducts(request.body);
    if (result["idList"]) {
      response.respond(200, "OK", {
        result,
      });
    } else {
      response.respond(400, "Please check your products' information");
    }
  });

  app.post("/file", async (request, response) => {
    const urls = await file.upload(request.files);

    response.respond(200, "OK", { urls });
  });

  app.patch("/vendor/products/:productId", async (request, response) => {
    var productId = request.params.productId;
    var changeParameters = request.body;

    const result = await vendor.updateProduct(productId, changeParameters);

    if (!!result.name) {
      response.respond(200, "OK", {
        result,
      });
    } else {
      response.respond(400, result);
    }
  });

  app.get("/vendor/products/:vendorId", async (request, response) => {
    parameters = request.body;
    parameters["vendorId"] = request.params.vendorId;

    const result = await vendor.getProducts(parameters);
    if (!!result.success) {
      response.respond(ErrorCode(result.message), result.message);
    } else if (!!result.productList) {
      response.respond(200, "OK", {
        result,
      });
    } else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });

  app.get("/vendor/vendorlist", async (request, response) => {
    const result = await vendor.getVendorList();
    response.respond(200, "OK", {
      result,
    });
  });

  app.delete("/vendor/product/:vendorId", async (request, response) => {
    var parameter = request.body;
    parameter["vendorId"] = request.params.vendorId;

    const result = await vendor.deleteProduct(parameter);

    console.log(result);
    if (result) {
      response.respond(200, "The product is deleted successfully");
    } else {
      response.respond(400, "Please check your product information. It has never existed or been already deleted");
    }
  });
};
