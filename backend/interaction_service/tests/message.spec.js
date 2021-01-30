const chai = require("chai"),
  { describe, before, after } = require("mocha"),
  db = require("../db/init"),
  Customer = require("../models/customer").Customer,
  Vendor = require("../models/vendor").Vendor,
  Message = require("../models/message").Message,
  message = require("../views/message"),
  { ErrorMessage } = require("../constants/error");

process.env.MONGO_URL = process.env.TEST_MONGO_URL;

describe("# Message view tests", async function () {
  this.timeout(0);

  let receiver;
  let sender;
  let secondSender;

  before("Create test users", async () => {
    await db.initialize();

    receiver = new Customer({
      name: "David Bowie",
      email: "davidbowie@buyo.com",
    });
    sender = new Customer({
      name: "Axl Rose",
      email: "axlrose@buyo.com",
    });
    secondSender = new Vendor({
      name: "Eddie Vedder",
      email: "eddievedder@buyo.com",
    });

    await receiver.save();
    await sender.save();
    await secondSender.save();

    receiver.id = receiver._id.toString();
    sender.id = sender._id.toString();
    secondSender.id = secondSender._id.toString();
  });

  describe("function: send", () => {
    it("should send a customer-customer message", async () => {
      const result = await message.send({
        userId: sender.id,
        userType: "customer",
        withId: receiver.id,
        withType: "customer",
        message: "Hello!",
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result).has.property("message");
      chai.expect(result.message.message).to.equal("Hello!");
      chai.expect(result.message.id).to.be.a("string");

      customerId = result.userId;
    });

    it("should fail with user not found error", async () => {
      const result = await message.send({
        userId: secondSender.id,
        userType: "customer", // this should have been vendor
        withId: receiver.id,
        withType: "customer",
        message: "Hello!",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.USER_NOT_FOUND);
    });

    it("should send a vendor-customer message", async () => {
      const result = await message.send({
        userId: secondSender.id,
        userType: "vendor",
        withId: receiver.id,
        withType: "customer",
        message: "Hello my customer!",
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result).has.property("message");
      chai.expect(result.message.message).to.equal("Hello my customer!");
      chai.expect(result.message.id).to.be.a("string");

      customerId = result.userId;
    });

    it("should fail with missing parameter error", async () => {
      const result = await message.send({
        userType: "customer",
        withType: "customer",
        message: "Hello!",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });
  });

  describe("function: getLastMessages", () => {
    it("should get last messages of a customer", async () => {
      const result = await message.getLastMessages({
        id: receiver.id,
        userType: "customer",
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result.messages).to.be.an("array");
      chai.expect(result.messages[0].user.name).to.equal("Eddie Vedder");
      chai.expect(result.messages[0].lastMessage).to.equal("Hello my customer!");
      chai.expect(result.messages[1].user.name).to.equal("Axl Rose");
      chai.expect(result.messages[1].lastMessage).to.equal("Hello!");
    });

    it("should fail with missing parameter error", async () => {
      const result = await message.getLastMessages({
        userType: "customer",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });
  });

  describe("function: getMessages", () => {
    it("should fail with missing parameter error", async () => {
      const result = await message.getMessages({
        userType: "customer",
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });

    it("should get vendor-customer messages", async () => {
      const result = await message.getMessages({
        id: receiver.id,
        userType: "customer",
        withId: secondSender.id,
        withType: "vendor",
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result.messages).to.be.an("array");
      chai.expect(result.messages[0].user.name).to.equal("Eddie Vedder");
      chai.expect(result.messages[0].message).to.equal("Hello my customer!");
    });

    it("should get customer-customer messages", async () => {
      const result = await message.getMessages({
        id: receiver.id,
        userType: "customer",
        withId: sender.id,
        withType: "customer",
      });

      chai.expect(result.success).to.be.true;
      chai.expect(result.messages).to.be.an("array");
      chai.expect(result.messages[0].user.name).to.equal("Axl Rose");
      chai.expect(result.messages[0].message).to.equal("Hello!");
    });
  });

  after("Delete added documents", async () => {
    await Promise.all([Customer.deleteMany(), Vendor.deleteMany(), Message.deleteMany()]);
  });
});
