const chai = require("chai"),
  mocha = require("mocha"),
  app = require("../app").App,
  request = require("supertest"),
  describe = mocha.describe,
  it = mocha.it;

describe("# Nearest Hospital Names", function () {
  it("should return a list of nearest hospitals", async function () {
    const response = await request(app).get("/nearesthospitals");

    chai.expect(response.body.status).to.be.an("object");
    chai.expect(response.body.status.success).to.equal(true);
    chai.expect(response.body.status.code).to.equal(200);
    chai.expect(response.body.data).to.be.an("object");
    chai.expect(response.body.data.names).to.be.an("array");
    chai.expect(response.body.data.count).to.be.an("number");

    response.body.data.names.forEach(function (name) {
      chai.expect(name).to.be.a("string");
    });
  });
  it("should return a list of nearest hospitals to Bogazici Uni", async function () {
    const response = await request(app).get("/nearesthospitals", {
      lat: 41.08542,
      long: 29.04885,
      radius: 2000
    });

    if (response.body.data.count) {
      // make sure request limit is not exceeded
      chai
        .expect(response.body.data.names)
        .to.be.an("array")
        .that.includes("Baltalimanı Family Health Center");
      chai
        .expect(response.body.data.names)
        .to.be.an("array")
        .that.includes("Acıbadem Etiler Medical Center");
    }
  });
  it("should return a list of nearest hospitals to Besiktas", async function () {
    const response = await request(app).get("/nearesthospitals", {
      lat: 41.0422,
      long: 29.0067,
      radius: 2000
    });

    if (response.body.data.count) {
      // make sure request limit is not exceeded
      chai
        .expect(response.body.data.names)
        .to.be.an("array")
        .that.includes("Nisantasi Family Health Center");
      chai
        .expect(response.body.data.names)
        .to.be.an("array")
        .that.includes("T. C. The Ministry of Health Sinanpaşa Family Health Center");
    }
  });
});
