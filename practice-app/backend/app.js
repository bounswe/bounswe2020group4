const express = require('express'),
  app = express(),
  bodyParser = require('body-parser'),
  db = require('./db'),
  routes = require('./routes'),
  port = 8756;

app.listen(port);
app.use(bodyParser.json());
routes.initialize(app);

console.log('Practice-app server started on: ' + port);