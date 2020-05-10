// Require the necessary libraries and classes.
const express = require('express'),
  app = express(),
  bodyParser = require('body-parser'),
  db = require('./db'),
  Constants = require('./constants'),
  routes = require('./routes');

app.listen(Constants.PORT); // Listen requests from the port.
app.use(bodyParser.json());
db.initialize(); // Initialize the database.
routes.initialize(app); // Start to listen the endpoints.

module.exports.App = app;

console.log('Practice-app server started on: ' + Constants.PORT);