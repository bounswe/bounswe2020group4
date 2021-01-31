const chai = require("chai"),
  { describe, before, after } = require("mocha"),
  db = require("../db/init"),
  Customer = require("../models/customer").Customer,
  Product = require("../models/product").Product,
  Comment = require("../models/comment").Comment,
  account = require("../../account_service/views/account"),
  comment = require("../views/comment"),
  { ErrorMessage } = require("../constants/error");
  ObjectId = require("mongoose").Types.ObjectId,


process.env.MONGO_URL = process.env.TEST_MONGO_URL;

describe("# Comment view tests", async function () {
  this.timeout(0);
  let user;
  let productId;
  let commentId;
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


  describe("function: comment", () => {
    it("should perform a successful comment to a product", async () => {
      const result = await comment.add({
        userId: user.id,
        productId: productId.toString(),
        comment: "best product",
        rating: 4.3
      });
      chai.expect(result.success).to.be.false;
      commentId = result.id;
    });

    it("should fail with missing parameter error", async () => {
      const result = await comment.add({
        userId: user.id,
        comment: "best product",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });

    it("should fail with product not found error", async () => {
      const result = await comment.add({
        userId: user.id,
        productId: 123,
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
