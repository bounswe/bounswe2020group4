const chai = require('chai'),
    mocha = require('mocha'),
    app = require('../app').App,
    request = require('supertest'),
    describe = mocha.describe,
    it = mocha.it;

describe('# Covid News', function () {
    it("should return a list of covid-19 news", async function () {
        const response = await request(app)
            .get("/covid/news");

        chai.expect(response.body.status).to.be.an('object');
        chai.expect(response.body.status.success).to.equal(true);
        chai.expect(response.body.status.code).to.equal(200);
        chai.expect(response.body.data).to.be.an('object');
        chai.expect(response.body.data.articles).to.be.an('array');

        response.body.data.articles.forEach(function (article) {
            chai.expect(article.title).to.be.a('string');
            chai.expect(article.description).to.be.a('string');
            chai.expect(article.url).to.be.a('string');
        });
    });
});