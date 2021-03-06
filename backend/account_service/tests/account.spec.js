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

    it("should fail with existing user", async () => {
      const result = await account.signup({
        name: "Bob Dylan",
        email: "bobdylan@buyo.com",
        password: 1234,
        userType: "customer",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.EMAIL_HAS_BEEN_USED);
    });

    it("should fail with missing parameter error", async () => {
      const result = await account.signup({
        name: "Bob Dylan",
        email: "bobdylan@buyo.com",
        password: 1234,
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });

    it("should perform a successful vendor signup", async () => {
      const result = await account.signup({
        name: "Bob Dylan",
        email: "bobdylan@buyo.com",
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
        name: "Bob Dylan",
        email: "bobdylan@buyo.com",
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
        name: "Bob Dylan",
        email: "bobdylan@buyo.com",
        password: 1234,
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });

    it("shouldn't find the specified user", async () => {
      const result = await account.login({
        name: "Bob Dylan",
        email: "bobdylan@buyo.com",
        password: 12345,
        userType: "customer",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.USER_NOT_FOUND);
    });

    it("should perform a successful vendor login", async () => {
      const result = await account.login({
        name: "Bob Dylan",
        email: "bobdylan@buyo.com",
        password: 1234,
        userType: "vendor",
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result).has.property("userId");
      chai.expect(result.userId).to.be.a("string");
      chai.expect(result.userId).to.equal(vendorId);
    });
  });

  describe("function: changePassword", () => {
    it("should fail with user not found error", async () => {
      const result = await account.changePassword({
        id: vendorId,
        password: 12345,
        userType: "customer",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.USER_NOT_FOUND);
    });

    it("should successfully change the vendor's password", async () => {
      const result = await account.changePassword({
        id: vendorId,
        password: 123456,
        userType: "vendor",
      });

      chai.expect(result.success).to.be.true;

      // Login attempt with old password
      let loginResult = await account.login({
        name: "Bob Dylan",
        email: "bobdylan@buyo.com",
        password: 12345,
        userType: "vendor",
      });

      chai.expect(loginResult.success).to.be.false;

      // Login attempt with new password
      loginResult = await account.login({
        name: "Bob Dylan",
        email: "bobdylan@buyo.com",
        password: 123456,
        userType: "vendor",
      });

      chai.expect(loginResult.success).to.be.true;
    });

    it("should fail with missing parameters error", async () => {
      const result = await account.changePassword({
        password: 12345,
        userType: "customer",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });
  });

  describe("function: updateAccountInfo", () => {
    it("should fail with user not found error", async () => {
      const result = await account.updateAccountInfo({
        id: vendorId,
        userType: "customer",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.USER_NOT_FOUND);
    });

    it("should successfully change the vendor's name", async () => {
      const result = await account.updateAccountInfo({
        id: vendorId,
        name: "BobDylan",
        userType: "vendor",
      });

      chai.expect(result.success).to.be.true;

      const accountResult = await account.getAccountInfo({
        id: vendorId,
        userType: "vendor",
      });

      chai.expect(accountResult.success).to.be.true;
      chai.expect(accountResult.account.name).to.equal("BobDylan");
    });

    it("should fail with missing parameters error", async () => {
      const result = await account.updateAccountInfo({
        userType: "customer",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });
  });

  describe("function: getAccountInfo", () => {
    it("should fail with user not found error", async () => {
      const result = await account.getAccountInfo({
        id: vendorId,
        userType: "customer",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.USER_NOT_FOUND);
    });

    it("should successfully get the vendor", async () => {
      const result = await account.getAccountInfo({
        id: vendorId,
        userType: "vendor",
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result.account.name).to.equal("BobDylan");
      chai.expect(result.account.email).to.equal("bobdylan@buyo.com");
    });

    it("should fail with missing parameters error", async () => {
      const result = await account.getAccountInfo({
        userType: "customer",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });
  });

  describe("function: getAccountInfo", () => {
    it("should fail with user not found error", async () => {
      const result = await account.getAccountInfo({
        id: vendorId,
        userType: "customer",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.USER_NOT_FOUND);
    });

    it("should successfully get the vendor", async () => {
      const result = await account.getAccountInfo({
        id: vendorId,
        userType: "vendor",
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result.account.name).to.equal("BobDylan");
      chai.expect(result.account.email).to.equal("bobdylan@buyo.com");
    });

    it("should fail with missing parameters error", async () => {
      const result = await account.getAccountInfo({
        userType: "customer",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });
  });

  describe("function: addAddress", () => {
    it("should fail with user not found error", async () => {
      const result = await account.addAddress({
        id: vendorId,
        address: "{}",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.USER_NOT_FOUND);
    });

    it("should successfully add the address", async () => {
      const result = await account.addAddress({
        id: customerId,
        address: JSON.stringify({ addressTitle: "Home", testField: true }),
      });

      chai.expect(result.success).to.be.true;

      const accountResult = await account.getAccountInfo({
        id: customerId,
        userType: "customer",
      });

      chai.expect(accountResult.success).to.be.true;
      chai.expect(accountResult.account.address[0].testField).to.equal(true);
    });

    it("should respond with address already exists error", async () => {
      const result = await account.addAddress({
        id: customerId,
        address: JSON.stringify({ addressTitle: "Home", testField: true }),
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.ADDRESS_ALREADY_EXISTS);
    });

    it("should fail with missing parameters error", async () => {
      const result = await account.addAddress({
        id: customerId,
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });
  });

  describe("function: updateAddress", () => {
    it("should fail with user not found error", async () => {
      const result = await account.updateAddress({
        id: vendorId,
        address: "{}",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.USER_NOT_FOUND);
    });

    it("should successfully update the address", async () => {
      const result = await account.updateAddress({
        id: customerId,
        address: JSON.stringify({ addressTitle: "Home", testField: false }),
      });

      chai.expect(result.success).to.be.true;

      const accountResult = await account.getAccountInfo({
        id: customerId,
        userType: "customer",
      });

      chai.expect(accountResult.success).to.be.true;
      chai.expect(accountResult.account.address[0].testField).to.equal(false);
    });

    it("should fail with missing parameters error", async () => {
      const result = await account.updateAddress({
        id: customerId,
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });
  });

  describe("function: deleteAddress", () => {
    it("should fail with user not found error", async () => {
      const result = await account.deleteAddress({
        id: vendorId,
        address: "{}",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.USER_NOT_FOUND);
    });

    it("should successfully delete the address", async () => {
      const result = await account.deleteAddress({
        id: customerId,
        address: JSON.stringify({ addressTitle: "Home" }),
      });

      chai.expect(result.success).to.be.true;

      const accountResult = await account.getAccountInfo({
        id: customerId,
        userType: "customer",
      });

      chai.expect(accountResult.success).to.be.true;
      chai.expect(accountResult.account.address).to.be.an("array");
      chai.expect(accountResult.account.address.length).to.equal(0);
    });

    it("should fail with missing parameters error", async () => {
      const result = await account.deleteAddress({
        id: customerId,
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });
  });
  describe("function: signInByGoogle", () => {
    it("should sign up with a Google Token", async () => {
      const result = await account.signInByGoogle({
        email: 'erdinc.oguz@gmail.com',
        name: 'Erdinc Oguz',
        token: '1234567890',
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result.userId).to.be.a('String');
    });

    it("should sign in with the same gmail and correct token", () => {
      const result = await account.signInByGoogle({
        email: 'erdinc.oguz@gmail.com',
        name: 'Erdinc Oguz',
        token: '1234567890',
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result.userId).to.be.a('String');
    });

    it("shouldn't signin with wrong token", () => {
      const result = await account.signInByGoogle({
        email: 'erdinc.oguz@gmail.com',
        name: 'Erdinc Oguz',
        token: '9876543210',
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.WRONG_GOOGLE_TOKEN);
    });

    it("should give error for missing parameters", () => {
      const result_token = await account.signInByGoogle({
        email: 'erdinc.oguz@gmail.com',
        name: 'Erdinc Oguz',
      });

      const result_email = await account.signInByGoogle({
        name: 'Erdinc Oguz',
        token: '9876543210',
      });

      const result_name = await account.signInByGoogle({
        email: 'erdinc.oguz@gmail.com',
        token: '9876543210',
      });

      chai.expect(result_token.success).to.be.false;
      chai.expect(result_email.success).to.be.false;
      chai.expect(result_name.success).to.be.false;
      chai.expect(result_email.message).to.equal(ErrorMessage.MISSING_PARAMETER);
      chai.expect(result_token.message).to.equal(ErrorMessage.MISSING_PARAMETER);
      chai.expect(result_name.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });

    it("should allow previously signed in users to sign in using Google", () => {
      const result = await account.signInByGoogle({
        name: "Bob Dylan",
        email: "bobdylan@buyo.com",
        token: "1029384756"
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result.userId).to.be.a('String');
    });

    it("should allow a user to signup even if they signed up using Google account to update password.", () => {
      const result = await account.signup({
        email: "erdinc.oguz@gmail.com",
        password: "1029384756",
        userType: "customer",
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result.userId).to.be.a("String");
    });
  });

  after("Delete added documents", async () => {
    await Promise.all([Customer.deleteMany(), Vendor.deleteMany()]);
  });

  describe("function: verifyAccount", () => {
    it("should verify customer's account", async () => {
      const result = await account.verifyAccount({
        id: customerId,
        userType: "customer",
      });

      chai.expect(result.success).to.be.true;
    });

    it("should verify vendor's account", async () => {
      const result = await account.verifyAccount({
        id: vendorId,
        userType: "vendor",
      });

      chai.expect(result.success).to.be.true;
    });

    it("should return user not found error", async () => {
      const result = await account.verifyAccount({
        id: 123,
        userType: "customer",
      });
      chai.expect(result.success).to.be.false;
    });

    it("should fail with missing parameters error", async () => {
      const result = await account.verifyAccount({
        id: customerId,
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });
  });
});
