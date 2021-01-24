const product = require("../views/product");
const recommendation = require("../views/recommendation");
const wishlist = require("../views/wishlist");
const cart = require("../views/cart");
const { ErrorCode } = require("../constants/error");

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
    if ( "name" in result && !("Error" in result)) {
      response.respond(200, "OK", {
        result,
      });
    } else {
      response.respond(404, "Product not found. Please check productId");
    }
  });

  /**
   * Returns product with given id.
   */
  app.get("/products/recommendation", async (request, response) => {
    const result = await recommendation.getProducts(request.query);

    if (result.success) {
      response.respond(200, "OK", {
        productList: result.productList,
      });
    } else {
      response.respond(ErrorCode(result.message), result.message);
    }
  });
  /**
   * Get wishlist of the user with given id
   */
  app.get("/wishlist", async (request, response) => {
    const products = await wishlist.getWishlist(request.query.customerId);

    response.respond(200, "OK", { products });
  });

  app.post("/cart", async (request, response) => {
    const result = await cart.updateCart(request.query);

    if (result) {
      response.respond(200, "OK");
    } else {
      response.respond(400, "Missing arguments.");
    }
  });

  app.get("/cart", async (request, response) => {
    const products = await cart.getCartProducts(request.query);
    if (products == false) {
      response.respond(404, "User not found");
    }

    response.respond(200, "OK", {
      products,
    });
  });

  app.delete("/cart", async (request, response) => {
    const success = await cart.emptyCart(request.query);

    if (success) {
      response.respond(200, "OK");
    } else {
      response.respond(400, "Missing arguments");
    }
  });
};
