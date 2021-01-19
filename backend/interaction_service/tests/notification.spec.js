const chai = require("chai"),
  { describe, before, after } = require("mocha"),
  db = require("../db/init"),
  Customer = require("../models/customer").Customer,
  Vendor = require("../models/vendor").Vendor,
  Notification = require("../models/notification").Notification,
  ObjectId = require("mongoose").Types.ObjectId,
  notification = require("../views/notification"),
  { ErrorMessage } = require("../constants/error");

process.env.MONGO_URL = process.env.TEST_MONGO_URL;

describe("# Notification view tests", async function () {
  this.timeout(0);

  let customerId;
  let vendorId;
  let productId;
  let orderId;

  before("Create test user", async () => {
    await db.initialize();

    const customer = new Customer({
      name: "Test",
      email: "test@example.com",
    });
    const vendor = new Vendor({
      name: "Test Vendor",
      email: "test@examplevendor.com",
    });

    await customer.save();
    await vendor.save();

    customerId = customer._id.toString();
    vendorId = vendor._id.toString();
    productId = ObjectId();
    orderId = ObjectId();

    const firstNotification = new Notification({
      userId: customer._id,
      userType: "customer",
      startTime: new Date(),
      name: "Discount",
      summary: "Test discount summary",
      actorType: "vendor",
      actorId: vendor._id,
      target: productId,
    });
    const secondNotification = new Notification({
      userId: customer._id,
      userType: "customer",
      startTime: new Date(),
      name: "Cancel order",
      summary: "Test order summary",
      actorType: "customer",
      actorId: customer._id,
      target: orderId,
    });

    await firstNotification.save();
    await secondNotification.save();
  });

  describe("function: getNotifications", () => {
    it("should get two notifications", async () => {
      const result = await notification.getNotifications({
        userId: customerId,
        userType: "customer",
      });

      chai.expect(result.success).to.be.true;

      const response = result.response;
      chai.expect(response["@context"]).to.equal("https://www.w3.org/ns/activitystreams");
      chai.expect(response.summary).to.equal("BUYO Notifications");
      chai.expect(response.type).to.equal("Collection");
      chai.expect(response.totalItems).to.equal(2);
      chai.expect(response.items.length).to.equal(2);

      response.items.forEach((notification) => {
        chai.expect(notification.type).to.equal("Update");
        chai.expect(["Cancel order", "Discount"]).include(notification.name);

        if (notification.name === "Cancel order") {
          chai.expect(notification.summary).to.equal("Test order summary");
          chai.expect(notification.target).to.equal(orderId.toString());
          chai.expect(notification.actor.id).to.equal(customerId.toString());
          chai.expect(notification.actor.type).to.equal("Person");
          chai.expect(notification.actor.name).to.equal("test@example.com");
        } else {
          chai.expect(notification.summary).to.equal("Test discount summary");
          chai.expect(notification.target).to.equal(productId.toString());
          chai.expect(notification.actor.id).to.equal(vendorId.toString());
          chai.expect(notification.actor.type).to.equal("Organization");
          chai.expect(notification.actor.name).to.equal("Test Vendor");
        }
      });
    });

    it("should fail with missing parameter error", async () => {
      const result = await notification.getNotifications({
        userId: customerId,
      });

      chai.expect(result.success).to.be.false;
      chai.expect(result.message).to.equal(ErrorMessage.MISSING_PARAMETER);
    });
  });

  after("Delete added documents", async () => {
    await Promise.all([Customer.deleteMany(), Vendor.deleteMany(), Notification.deleteMany()]);
  });
});
