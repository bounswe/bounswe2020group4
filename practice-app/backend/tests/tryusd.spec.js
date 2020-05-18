const chai = require('chai'),
    mocha = require('mocha'),
    app = require('../app').App,
    request = require('supertest'),
    describe = mocha.describe,
    it = mocha.it;

describe('# Try to Usd', function () {
    it("should return a number", async function () {
        const response = await request(app)
            .get("/tryusd");

        chai.expect(response.body.status).to.be.an('object');
        chai.expect(response.body.status.success).to.equal(true);
        chai.expect(response.body.status.code).to.equal(200);
        chai.expect(response.body.data).to.be.an('object');
        chai.expect(response.body.data.TRY).to.be.a('number');

    });

    it("should respond with exchangerateapi token error", async function () {
    process.env.TRYUSD_API_KEY = "wrong-api-key";

    const response = await request(app)
        .get("/tryusd");

    chai.expect(response.body.status).to.be.an('object');
    chai.expect(response.body.status.success).to.equal(false);
    chai.expect(response.body.status.code).to.equal(404);
    chai.expect(response.body.data).to.equal(undefined);
    chai.expect(response.body.error).to.equal(undefined);
});

});
