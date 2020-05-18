const nearbyVendors = require('./controllers/nearestVendor');
const tryToUsd = require('./controllers/tryusd');
// Initialize the routes.
module.exports.initialize = function (app) {
    // Redirect the GET /vendor/nearest request to the getNearestVendor function.
    app.get("/vendor/nearest", nearbyVendors.getNearestVendor);
    // Redirect the GET /tryusd request to the getTryUsd function.
    app.get("/tryusd", tryToUsd.getTryUsd);
}
