const chai = require('chai'),
    mocha = require('mocha'),
    app = require('../app').App,
    request = require('supertest'),
    describe = mocha.describe,
    it = mocha.it;

describe('# Weather for Next Days', function () {
    it("should return the first and the last days' data", async function () {
        const response = await request(app)
            .get("/weather/daily");

        chai.expect(response.body.status).to.be.an('object');
        chai.expect(response.body.status.success).to.equal(true);
        chai.expect(response.body.status.code).to.equal(200);
        chai.expect(response.body.error).to.equal(false);
        chai.expect(response.body.data).to.be.an('object');
        chai.expect(response.body.data.day1).to.be.an('object');
        chai.expect(response.body.data.day1.minTemp).to.be.a('number');
        chai.expect(response.body.data.day1.maxTemp).to.be.a('number');
        chai.expect(response.body.data.day1.desc).to.be.a('string');
        chai.expect(response.body.data.day1.icon).to.be.a('string');
        chai.expect(response.body.data.day7).to.be.an('object');
        chai.expect(response.body.data.day7.minTemp).to.be.a('number');
        chai.expect(response.body.data.day7.maxTemp).to.be.a('number');
        chai.expect(response.body.data.day7.desc).to.be.a('string');
        chai.expect(response.body.data.day7.icon).to.be.a('string');
    });
});
