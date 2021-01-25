const chai = require("chai"),
  { describe, before, after } = require("mocha"),
  db = require("../db/init"),
  Customer = require("../models/customer").Customer,
  Product = require("../models/product").Product,
  Comment = require("../models/comment").Comment,
  account = require("../../account_service/views/account"),
  comment = require("../views/comment"),
  { ErrorMessage } = require("../constants/error");

process.env.MONGO_URL = process.env.TEST_MONGO_URL;

describe("# Comment view tests", async function () {
  this.timeout(0);

  before("Create test user", async () => {
    await db.initialize();
  });
  let customerId;
  let productId = await Product.findOne(); 
  let commentId;
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


  describe("function: comment", () => {
    it("should perform a successful comment to a product", async () => {
      const result = await comment.add({
        userId: customerId,
        productId: productId,
        comment: "best product",
        rating: 4.3,
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result.id).to.be.a("string");

      commentId = result.id;
    });


    it("should fail with missing parameter error", async () => {
      const result = await comment.add({
        userId: customerId,
        comment: "best product",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });

    it("should fail with product not found error", async () => {
      const result = await comment.add({
        userId: customerId,
        productId: "wrongId",
        comment: "best product",
        rating: 4.3,
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.PRODUCT_NOT_FOUND);
    });

    it("should perform a successful comment delete from a product", async () => {
        const result = await comment.delete({
          id: commentId,
        });
  
        chai.expect(result.success).to.be.true;
      });
  });

 
});