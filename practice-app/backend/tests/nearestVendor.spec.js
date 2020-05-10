const chai = require('chai'),
    mocha = require('mocha'),
    app = require('../app').App,
    Constants = require('../constants'),
    request = require('supertest'),
    describe = mocha.describe,
    it = mocha.it;

describe('# Nearest Vendor', function () {
    it("should return a vendor object", async function () {
        const response = await request(app)
            .get("/vendor/nearest");

        chai.expect(response.body.status).to.be.an('object');
        chai.expect(response.body.status.success).to.equal(true);
        chai.expect(response.body.status.code).to.equal(200);
        chai.expect(response.body.data).to.be.an('object');
        chai.expect(response.body.data.vendor).to.be.an('object');
        chai.expect(response.body.data.vendor.location).to.be.an('object');
        chai.expect(response.body.data.vendor.name).to.be.a('string');
        chai.expect(response.body.data.vendor.location.longitude).to.be.a('number');
        chai.expect(response.body.data.vendor.location.latitude).to.be.a('number');
        chai.expect(response.body.data.distance).to.be.a('number');
    });

    it("should respond with googleapi token error", async function () {
        Constants.GOOGLE_API_KEY = "wrong-api-key";

        const response = await request(app)
            .get("/vendor/nearest");

        chai.expect(response.body.status).to.be.an('object');
        chai.expect(response.body.status.success).to.equal(false);
        chai.expect(response.body.status.code).to.equal(404);
        chai.expect(response.body.data).to.equal(undefined);
        chai.expect(response.body.error).to.be.an('string');
    });
});