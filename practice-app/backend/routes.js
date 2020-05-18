const nearbyVendors = require('./controllers/nearestVendor');
const weatherDays = require('./controllers/weatherNextDays');

// Initialize the routes.
module.exports.initialize = function (app) {
    // Redirect the GET /vendor/nearest request to the getNearestVendor function.
    app.get("/vendor/nearest", nearbyVendors.getNearestVendor);
    // Redirect the GET /weather/daily request to the weatherNextDays function.
    app.get("/weather/daily", weatherDays.weatherNextDays);
}
