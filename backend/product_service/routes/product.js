const product = require("../views/product");
const wishlist = require("../views/wishlist");
const account = require("../views/account");
const db = require("../db/product");

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
  
  app.post("/like", async (request, response) => {
    const result = await wishlist.like(request.query);

    response.respond(200, "OK");
  });

  app.get("/wishlist", async (request, response) => {
    const products = await wishlist.getWishlist(request.query.customerId);

    response.respond(200, "OK", { products });
  });

  app.post("/login", async (request, response) => {
    const result = await account.login(request.query);
    if (result) {
      response.respond(200, "OK", { userId: result });
    } else {
      response.respond(404, "User not found");
    }
  });

  app.post("/signup", async (request, response) => {
    const result = await account.signup(request.query);
    let data = { userId: result };
    if(result == "This email has been already used"){
      data = { info: result };
    }
    response.respond(200,"OK",info);
  });

  app.post("/db/init", async (request, response) => {
    await db.initializeMockDB();

    response.respond(200, "OK");
  });
};
