// Require the necessary libraries and classes.
const express = require("express"),
  app = express(),
  bodyParser = require("body-parser"),
  db = require("./db/init"),
  cors = require("cors"),
  server = require("http").createServer(app),
  socket = require("./utils/socket"),
  routes = require("./routes/interaction");

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

server.listen(process.env.PORT); // Listen requests from the port.
app.use(bodyParser.json());
app.use(cors());
db.initialize(); // Initialize the database.
socket.initialize(server);
routes.initialize(app); // Start to listen the endpoints.

module.exports.App = app;

console.log("Account service server started on: " + process.env.PORT);
