const file = require("../views/file");
const vendor = require("../views/vendor");
const { ErrorCode } = require("../constants/error");


module.exports.initialize = (app) => {
  app.post("/vendor/products", async (request, response) => {
    const result = await product.addProducts(request.body.products);
    if (result) {
      response.respond(200, "OK", {
        result,
      });
    } else {
      response.respond(404, "Product not found");
    }
  });

  app.post("/file", async (request, response) => {
    const urls = await file.upload(request.files);

    response.respond(200, "OK", { urls });
  });


  app.patch("/vendor/products/:id", async (request, response) => {
    var productId = request.params.id
    var changeParameters = request.body;

    const result = await vendor.updateProduct(productId,changeParameters);

    if (!!result.name) {
      response.respond(200, "OK", {
        result,
      });
    } 
    else {
      response.respond(200, 300);
    }
  });

};
