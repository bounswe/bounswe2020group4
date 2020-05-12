const chai = require('chai'),
    mocha = require('mocha'),
    app = require('../app').App,
    Constants = require('../constants'),
    request = require('supertest'),
    describe = mocha.describe,
    it = mocha.it;

describe('# COVID-19 Turkey Data', function () {
    it("should return 7 lines of data and a date", async function () {
        const response = await request(app)
            .get("/covid19");

        chai.expect(response.body.status).to.be.an('object');
        chai.expect(response.body.status.success).to.equal(true);
        chai.expect(response.body.status.code).to.equal(200);
        chai.expect(response.body.data).to.be.an('object');
        chai.expect(response.body.data.NewConfirmed).to.be.an('number');
        chai.expect(response.body.data.TotalConfirmed).to.be.an('number');
        chai.expect(response.body.data.NewDeaths).to.be.a('number');
        chai.expect(response.body.data.TotalDeaths).to.be.a('number');
        chai.expect(response.body.data.NewRecovered).to.be.a('number');
        chai.expect(response.body.data.TotalRecovered).to.be.a('number');
        chai.expect(response.body.data.ActiveCases).to.be.a('number');
        chai.expect(response.body.data.lastUpdate).to.be.a('string');
    });
});