const nearbyVendors = require('./controllers/nearestVendor');
const apod = require('./controllers/apod');

// Initialize the routes.
module.exports.initialize = function (app) {
    // Redirect the GET /vendor/nearest request to the getNearestVendor function.
    app.get("/vendor/nearest", nearbyVendors.getNearestVendor);
    // Redirect the GET /apod request to the getPictureOfToday function.
    app.get("/apod", apod.getPictureOfToday);
}
