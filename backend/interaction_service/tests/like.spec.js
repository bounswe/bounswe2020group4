const chai = require("chai"),
  { describe, before, after } = require("mocha"),
  db = require("../db/init"),
  Customer = require("../models/customer").Customer,
  Product = require("../models/product").Product,
  Like = require("../models/like").Like,
  like = require("../views/like"),
  account = require("../../account_service/views/account"),
  { ErrorMessage } = require("../constants/error");

process.env.MONGO_URL = process.env.TEST_MONGO_URL;

describe("# Comment view tests", async function () {
  this.timeout(0);

  before("Create test user", async () => {
    await db.initialize();
  });

  let customerId;
  let productId = await Product.findOne(); 
  describe("function: signup", () => {
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

  describe("function: like", () => {
    it("should perform a successful like to a product", async () => {
      const result = await like.like({
        customerId: customerId,
        productId: productId,
      });

      chai.expect(result.success).to.be.true;
    });


    it("should return false", async () => {
      const result = await comment.add({
        customerId: customerId,
        productId: "best product",
      });
      chai.expect(result.success).to.be.false;
    });

  });

 
});
