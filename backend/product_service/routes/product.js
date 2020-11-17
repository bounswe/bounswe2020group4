const product = require("../views/product");

// Initialize the routes.
module.exports.initialize = (app) => {
  app.get("/categories", async (request, response) => {
    const categories = await product.getProductCategories();

    response.respond(200, "OK", {
      categories,
    });
  });

  app.get("/products", async (request, response) => {
    const products = await product.getProducts(request.query);
    response.respond(200, "OK", {
      products,
    });
  });
};
