// Require the necessary libraries and classes.
const express = require("express"),
  app = express(),
  bodyParser = require("body-parser"),
  db = require("./db/init"),
  cors = require("cors"),
  routes = require("./routes/admin");

require("dotenv").config(); // Require the dotenv for constants.

/**
 *
 * @param {String} code: Status code
 * @param {String} message: Status message
 * @param {Object} data: Payload
 */
express.response.respond = function (code, message, data) {
  this.status(code).send({
    status: { code, message },
    data,
  });
};

express.request.extractParams = function () {
  return Object.keys(this.body).length ? this.body : this.query;
};

app.listen(process.env.PORT); // Listen requests from the port.
app.use(bodyParser.json());
app.use(cors());
db.initialize(); // Initialize the database.
routes.initialize(app); // Start to listen the endpoints.

module.exports.App = app;

console.log("Admin service server started on: " + process.env.PORT);
