// Require the necessary libraries and classes.
const express = require("express"),
  app = express(),
  bodyParser = require("body-parser"),
  db = require("./db/product"),
  routes = require("./routes/product");

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
db.initialize(); // Initialize the database.
routes.initialize(app); // Start to listen the endpoints.

module.exports.App = app;

console.log("Practice-app server started on: " + process.env.PORT);
