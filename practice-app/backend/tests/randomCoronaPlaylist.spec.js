const chai = require('chai'),
    mocha = require('mocha'),
    app = require('../app').App,
    request = require('supertest'),
    describe = mocha.describe,
    it = mocha.it;

describe('# Random Corona Playlist', function () {
    it("should return a playlist object with 4 parameters", async function () {
        const response = await request(app).get("/random-playlist");

        chai.expect(response.body.status).to.be.an('object');
        chai.expect(response.body.status.success).to.equal(true);
        chai.expect(response.body.status.code).to.equal(200);
        chai.expect(response.body.data).to.be.an('object');
        chai.expect(response.body.data.playlist_id).to.be.an('string');
        chai.expect(response.body.data.name).to.be.an('string');
        chai.expect(response.body.data.description).to.be.an('string');
        chai.expect(response.body.data.owner).to.be.an('string');
        chai.expect(response.body.data).to.be.an('object');
    });

    it("should respond with wrong auth error", async function () {
        process.env.SPOTIFY_CLIENT_ID = "wrong-spotify-id";
        process.env.SPOTIFY_CLIENT_SECRET = "wrong-spotify-secret";

        const response = await request(app).get("/random-playlist");

        chai.expect(response.body.status).to.be.an('object');
        chai.expect(response.body.status.success).to.equal(false);
        chai.expect(response.body.status.code).to.equal(404);
        chai.expect(response.body.data).to.equal(undefined);
    });
});