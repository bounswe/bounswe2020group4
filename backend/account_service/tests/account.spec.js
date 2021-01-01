const chai = require("chai"),
  { describe, before, after } = require("mocha"),
  db = require("../db/init"),
  Customer = require("../models/customer").Customer,
  Vendor = require("../models/vendor").Vendor,
  account = require("../views/account"),
  { ErrorMessage } = require("../constants/error");

process.env.MONGO_URL = process.env.TEST_MONGO_URL;

describe("# Account view tests", async function () {
  this.timeout(0);

  let customerId;
  let vendorId;

  before("Create test user", async () => {
    await db.initialize();
  });

  describe("function: signup", () => {
    it("should perform a successful customer signup", async () => {
      const result = await account.signup({
        name: "Koray Cetin",
        email: "koray@buyo.com",
        password: 1234,
        userType: "customer",
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result).has.property("userId");
      chai.expect(result.userId).to.be.a("string");

      customerId = result.userId;
    });

    it("should fail with existing user", async () => {
      const result = await account.signup({
        name: "Koray Cetin",
        email: "koray@buyo.com",
        password: 1234,
        userType: "customer",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.EMAIL_HAS_BEEN_USED);
    });

    it("should fail with missing parameter error", async () => {
      const result = await account.signup({
        name: "Koray Cetin",
        email: "koray@buyo.com",
        password: 1234,
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });

    it("should perform a successful vendor signup", async () => {
      const result = await account.signup({
        name: "Koray Cetin",
        email: "koray@buyo.com",
        userType: "vendor",
        password: 1234,
        company: "BUYO",
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result).has.property("userId");
      chai.expect(result.userId).to.be.a("string");

      vendorId = result.userId;
    });
  });

  describe("function: login", () => {
    it("should perform a successful customer login", async () => {
      const result = await account.login({
        name: "Koray Cetin",
        email: "koray@buyo.com",
        password: 1234,
        userType: "customer",
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result).has.property("userId");
      chai.expect(result.userId).to.be.a("string");
      chai.expect(result.userId).to.equal(customerId);
    });

    it("should fail with a missing parameter error", async () => {
      const result = await account.login({
        name: "Koray Cetin",
        email: "koray@buyo.com",
        password: 1234,
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });

    it("should't found the specified user", async () => {
      const result = await account.login({
        name: "Koray Cetin",
        email: "koray@buyo.com",
        password: 12345,
        userType: "customer",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.USER_NOT_FOUND);
    });

    it("should perform a successful vendor login", async () => {
      const result = await account.login({
        name: "Koray Cetin",
        email: "koray@buyo.com",
        password: 1234,
        userType: "vendor",
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result).has.property("userId");
      chai.expect(result.userId).to.be.a("string");
      chai.expect(result.userId).to.equal(vendorId);
    });
  });

  after("Delete added documents", async () => {
    await Promise.all([Customer.deleteMany(), Vendor.deleteMany()]);
  });
});
