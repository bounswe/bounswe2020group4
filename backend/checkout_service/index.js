// Require the necessary libraries and classes.
const express = require("express"),
  app = express(),
  bodyParser = require("body-parser"),
  db = require("./db/init"),
  cors = require("cors"),
  routes = require("./routes/account");

require("dotenv").config(); // Require the dotenv for constants.

express.response.respond = function (code, message, data) {
  this.status(code).send({
    status: { code, message },
    data,
  });
};

express.request.error = function () {
  return {
    url: this.url,
    params: this.params,
    query: this.query,
    body: this.body,
    headers: this.headers,
  };
};

app.listen(process.env.PORT); // Listen requests from the port.
app.use(bodyParser.json());
app.use(cors());
db.initialize(); // Initialize the database.
routes.initialize(app); // Start to listen the endpoints.

module.exports.App = app;

console.log("Account service server started on: " + process.env.PORT);
