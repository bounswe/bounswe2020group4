const chai = require("chai"),
  { describe, before, after } = require("mocha"),
  db = require("../db/init"),
  Customer = require("../models/customer").Customer,
  CartProduct = require("../models/cart_products").CartProduct,
  cart = require("../views/cart");

  process.env.MONGO_URL = process.env.TEST_MONGO_URL;

  describe("# Cart view tests", async function () {
      // TODO(eridincu): After adding error handling to cart view, add related unit tests for each error.
      this.timeout(0);

      let customerId;
      let productId;
      let productInfo;

      before("Create test user", async () => {
        await db.initialize();

        it("should perform a successful customer signup", async () => {
            const result = await account.signup({
              name: "Bob Dylan",
              email: "bobdylan@buyo.com",
              password: 1234,
              userType: "customer",
            });
      
            chai.expect(result.success).to.be.true;
            chai.expect(result).has.property("userId");
            chai.expect(result.userId).to.be.a("string");
      
            customerId = result.userId;
          });
      });

      describe("function: updateCart", () => {
          it("should add a new product to cart for a customer", async () => {
            const result = await cart.updateCart({
                customerId: customerId,
                productId: productId,
                productInfo: productInfo,
            });

            chai.expect(result.success).to.be.true;
          });

      });

      describe("function: getCartProducts", () => {
        it("should return the product that was added in the previous test", async () => {
            const result = await cart.getCartProducts({
                customerId: customerId,
            });

            chai.expect(result.success).to.be.true;
            chai.expect(result.data.products).to.be.instanceOf(Array);
            chai.expect(result.data.products).to.have.length.above(0);
            chai.expect(result.data.totalPrice).to.be.not.null;
            chai.expect(result.data.discountedPrice).to.be.not.null;
        });

        it("should give an error when missing customer id", async () => {
            const result = await cart.getCartProducts({
                customerId: null,
            });

            chai.expect(result.success).to.be.false;
            chai.expect(result.message).to.equal("User not found");
        });
      });

      describe("function: emptyCart", () => {
          it("should empty the cart", async () => {
            const result = await cart.emptyCart({
                id: customerId,
            });

            chai.expect(result.success).to.be.true;
          });

          it("should give an error when missing customer id", async () => {
              const result = await cart.emptyCart({
                  id: null,
              });

              chai.expect(result.success).to.be.false;
              chai.expect(result.message).to.equal("Missing arguments");
          });
      });
      
      after("Delete added documents", async () => {
        await Promise.all([CartProduct.deleteMany(), Customer.deleteMany()]);
      });

  });