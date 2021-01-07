const chai = require("chai"),
  { describe, before, after } = require("mocha"),
  db = require("../db/init"),
   Product = require("../models/product").Product,
   Vendor = require("../models/vendor").Vendor,
   Comment = require("../models/comment").Comment,
   Customer = require("../models/customer").Customer,
  { ErrorMessage } = require("../constants/error");

process.env.MONGO_URL = process.env.TEST_MONGO_URL;

describe("# Account view tests", async function () {
  this.timeout(0);

  let customerId;
  let vendorId;

  before("Create test user", async () => {
    await db.initialize();
  });

  describe("function: getProducts", () => {
    it("should get products correctly", async () => {
      const result = await Product.getProducts();


      chai.expect(result.success).to.be.true;
      chai.expect(result).to.be.instanceof(Array);
      chai.expect(result).to.have.length.above(0);
    });

    it("should fail with empty array", async () => {
      const result = await  await Product.getProducts();

      chai.expect(result.success).to.be.false;
      chai.expect(result).to.be.instanceof(Array);
      chai.expect(result).to.have.length.equal(0);
    });

  });


  describe("function: getProductCategories", () => {
    it("should get categories correctly", async () => {
      const result = await Product.getProductCategories();

      chai.expect(result.success).to.be.true;
      chai.expect(result).to.be.instanceof(Array);
      chai.expect(result).to.have.length.above(0);
    });

    it("should fail with empty array", async () => {
      const result = await  await Product.getProductCategories();

      chai.expect(result.success).to.be.false;
      chai.expect(result).to.be.instanceof(Array);
      chai.expect(result).to.have.length.equal(0);
    });

  });


  after("Delete added documents", async () => {
    await Promise.all([Customer.deleteMany(), Vendor.deleteMany(),Product.deleteMany(),Comment.deleteMany(),Customer.deleteMany()]);
  });
});