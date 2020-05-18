const chai = require('chai'),
    //chai.use(require('chai-url'));
    mocha = require('mocha'),
    app = require('../app').App,
    request = require('supertest'),
    describe = mocha.describe,
    it = mocha.it;

describe('# Astronomy picture of today', function () {
    it("should return image url, title and copyright", async function () {
        const response = await request(app).get("/apod");

        chai.expect(response.body.status).to.be.an('object');
        chai.expect(response.body.status.success).to.equal(true);
        chai.expect(response.body.status.code).to.equal(200);
        chai.expect(response.body.data).to.be.an('object');
        //chai.expect(response.body.data.apodURL).to.have.protocol('https');
        chai.expect(response.body.data.apodTitle).to.be.a('string');
        chai.expect(response.body.data.apodCopyright).to.be.a('string');
    });

    it("should respond with nasa api token error", async function () {
        process.env.NASA_API_KEY = "wrong-api-key";

        const response = await request(app).get("/apod");

        chai.expect(response.body.status).to.be.an('object');
        chai.expect(response.body.status.success).to.equal(false);
        chai.expect(response.body.status.code).to.equal(404);
        chai.expect(response.body.data).to.equal(undefined);
        chai.expect(response.body.error).to.be.an('string');
    });
});
