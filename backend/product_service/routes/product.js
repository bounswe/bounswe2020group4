const product = require("../views/product");
const wishlist = require("../views/wishlist");

module.exports.initialize = (app) => {
  /**
   * Returns the whole categories present in the app.
   */
  app.get("/categories", async (request, response) => {
    const categories = await product.getProductCategories();

    response.respond(200, "OK", {
      categories,
    });
  });

  /**
   * Gets products with some filter(category, search, attribute etc.)
   */
  app.get("/products", async (request, response) => {
    const products = await product.getProducts(request.query);
    response.respond(200, "OK", {
      products,
    });
  });

  /**
   * Returns product with given id.
   */
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

  /**
   * Get wishlist of the user with given id
   */
  app.get("/wishlist", async (request, response) => {
    const products = await wishlist.getWishlist(request.query.customerId);

    response.respond(200, "OK", { products });
  });
};
