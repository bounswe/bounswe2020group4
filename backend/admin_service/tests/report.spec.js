const chai = require("chai"),
  { describe, before, after } = require("mocha"),
  db = require("../db/init"),
  Customer = require("../models/customer").Customer,
  Product = require("../models/product").Product,
  Comment = require("../models/comment").Comment,
  ProductReport = require("../models/productReport").ProductReport,
  CommentReport = require("../models/commentReport").CommentReport,
  report = require("../views/report"),
  { ErrorMessage } = require("../constants/error");
  ObjectId = require("mongoose").Types.ObjectId,

process.env.MONGO_URL = process.env.TEST_MONGO_URL;

describe("# Report view tests", async function () {
  this.timeout(0);
  let productId;
  let commentId;
  before("Get test product and comment", async () => {
    await db.initialize();

    const product = new Product({
      name: "String",
      imageUrl: "image.com",
      category: ["clothes"],
      rating: 3.2,
      price: 100,
      originalPrice: 90,
      productInfos: "String",
      brand: "String",
      sizes: ["String"],
      colors: ["String"],
      description: "String",
      vendorId: ObjectId(),
    });

    const comment = new Comment({
      userId: ObjectId(),
      productId: ObjectId(),
      rating: 3.2,
      text: "String"
    });

    await product.save();
    await comment.save();
    productId = product._id.toString();
    commentId = comment._id.toString();
  });



  describe("function: reportProduct", () => {
    it("should perform a successful report to a product", async () => {
      const result = await report.reportProduct({
        productId: productId,
        message: "message",
      });

      chai.expect(result.success).to.be.true;
    });

    it("should return false with product not found error", async () => {
      const result = await report.reportProduct({
        productId: 123,
        message: "message",
      });
      chai.expect(result.success).to.be.false;
    });

    it("should return false with missing parameter error", async () => {
      const result = await report.reportProduct({
        productId: productId,
      });
      chai.expect(result.success).to.be.false;
    });
  });

  describe("function: reportComment", () => {
    it("should perform a successful report to a comment", async () => {
      const result = await report.reportComment({
        commentId: commentId,
        message: "message"
      });

      chai.expect(result.success).to.be.true;
    });

    it("should return false with comment not found error", async () => {
      const result = await report.reportComment({
        commentId: 123,
        message: "message",
      });
      chai.expect(result.success).to.be.false;
    });

    it("should return false with missing parameter error", async () => {
      const result = await report.reportComment({
        commentId: commentId,
      });
      chai.expect(result.success).to.be.false;
    });
  });
});