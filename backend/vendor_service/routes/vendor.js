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


  app.get("/vendor/products", async (request, response) => {
    const result = await vendor.getProducts(request.query);

    console.log("***********")
    console.log("***********")
    console.log("***********")
    console.log(result)
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

};
