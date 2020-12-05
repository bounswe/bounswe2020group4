const product = require("../views/product");
const wishlist = require("../views/wishlist");

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

  app.get("/product", async (request, response) => {
    const result = await product.getProduct(request.query);
    if (result) {
      response.respond(200, "OK", {
        result,
      });
    } else {
      response.respond(404, "Product not found");
    }
  });

  app.get("/wishlist", async (request, response) => {
    const products = await wishlist.getWishlist(request.query.customerId);

    response.respond(200, "OK", { products });
  });
};
