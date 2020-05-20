const chai = require('chai'),
    mocha = require('mocha'),
    app = require('../app').App,
    request = require('supertest'),
    describe = mocha.describe,
    it = mocha.it;

describe('# Suggest Book', function () {
    it("should return a list of similar books object", async function () {
        const response = await request(app)
            .post("/suggestBook")
            .query({q : 'bible'});

        chai.expect(response.body.status).to.be.an('object');
        chai.expect(response.body.status.success).to.equal(true);
        chai.expect(response.body.status.code).to.equal(200);
        chai.expect(response.body.data).to.be.an('object');
        chai.expect(response.body.data.book).to.be.an('array');
        response.body.data.book.forEach(function (book) {
            chai.expect(book.title).to.be.a('string');
            chai.expect(book.image_url).to.be.a('string');
            chai.expect(article.authors).to.be.a('object');
        });
    });

    it("should respond with goodreads token error", async function () {
        process.env.GOODREADS_API_KEY = "wrong-api-key";

        const response = await request(app)
            .post("/suggestBook")
            .query({q : 'catch22'});

        chai.expect(response.body.status).to.be.an('object');
        chai.expect(response.body.status.success).to.equal(false);
        chai.expect(response.body.status.code).to.equal(404);
        chai.expect(response.body.data).to.equal(undefined);
        chai.expect(response.body.error).to.be.an('string');
    });

});
