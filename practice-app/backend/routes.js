const nearbyVendors = require('./controllers/nearestVendor');
const suggestBook = require('./controllers/suggestBook')

// Initialize the routes.
module.exports.initialize = function (app) {
    // Redirect the GET /vendor/nearest request to the getNearestVendor function.
    app.get("/vendor/nearest", nearbyVendors.getNearestVendor);

    app.post("/suggestBook", suggestBook.suggestBook);
}
