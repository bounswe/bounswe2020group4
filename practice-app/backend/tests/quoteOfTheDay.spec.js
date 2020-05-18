const chai = require('chai'),
    mocha = require('mocha'),
    app = require('../app').App,
    Constants = require('../constants'),
    request = require('supertest'),
    describe = mocha.describe,
    it = mocha.it;

describe('# Quote', function () {
    it("should return a quote and its author", async function () {
        const response = await request(app)
            .get("/quote");

        chai.expect(response.body.status).to.be.an('object');
        chai.expect(response.body.status.success).to.equal(true);
        chai.expect(response.body.status.code).to.equal(200);
        chai.expect(response.body.data).to.be.an('object');
        chai.expect(response.body.data.quote).to.be.an('string');
        chai.expect(response.body.data.author).to.be.an('string');
    });
});