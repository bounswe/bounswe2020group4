const file = require("../views/file");

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
};