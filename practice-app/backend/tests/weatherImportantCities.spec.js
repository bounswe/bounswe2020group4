const chai = require('chai'),
    mocha = require('mocha'),
    app = require('../app').App,
    request = require('supertest'),
    describe = mocha.describe,
    it = mocha.it;

describe('# Weather for Important Cities', function () {
    it("should return a list of current weather for given cities", async function () {
        const response = await request(app)
            .get("/weather-important-cities");

        chai.expect(response.body.status).to.be.an('object');
        chai.expect(response.body.status.success).to.equal(true);
        chai.expect(response.body.status.code).to.equal(200);
        chai.expect(response.body.data).to.be.an('object');
        chai.expect(response.body.data.currentWeathers).to.be.an('array');

        response.body.data.currentWeathers.forEach(function (weather) {
            chai.expect(weather.cityName).to.be.a('string');
            chai.expect(weather.currentTemp).to.be.a('number');
            chai.expect(weather.description).to.be.a('string');
            chai.expect(weather.iconUrl).to.be.a('string');
        });
    });
});