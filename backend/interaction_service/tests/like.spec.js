const chai = require("chai"),
  { describe, before, after } = require("mocha"),
  db = require("../db/init"),
  Customer = require("../models/customer").Customer,
  Product = require("../models/product").Product,
  Like = require("../models/like").Like,
  like = require("../views/like"),
  account = require("../../account_service/views/account"),
  { ErrorMessage } = require("../constants/error");
  ObjectId = require("mongoose").Types.ObjectId,

process.env.MONGO_URL = process.env.TEST_MONGO_URL;

describe("# Like view tests", async function () {
  this.timeout(0);
  let user;
  let productId;
  before("Create test user", async () => {
    await db.initialize();
    product = await Product.findOne();

    user = new Customer({
      name: "Bob Dylan",
      email: "bobdylan@buyo.com"
    });

    await user.save();
    user.id = user._id.toString();
    productId = ObjectId();
    
  });



  describe("function: like", () => {
    it("should perform a successful like to a product", async () => {
      const result = await like.like({
        customerId: user.id,
        productId: productId.toString(),
      });

      chai.expect(result.success).to.be.true;
    });

    it("should return false with product not found error", async () => {
      const result = await like.like({
        customerId: user.id,
        productId: "123",
      });
      chai.expect(result.success).to.be.false;
    });

    it("should return false with customer not found error", async () => {
      const result = await like.like({
        customerId: "customer",
        productId: productId.toString(),
      });
      chai.expect(result.success).to.be.false;
    });
  });
});
