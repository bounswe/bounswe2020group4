const nearbyVendors = require('./controllers/nearestVendor');
const quoteOfTheDay = require('./controllers/quoteOfTheDay');


// Initialize the routes.
module.exports.initialize = function (app) {
    // Redirect the GET /vendor/nearest request to the getNearestVendor function.
    app.get("/vendor/nearest", nearbyVendors.getNearestVendor);

    // Redirect GET /quote request to getQuote function
    app.get("/quote", quoteOfTheDay.getQuote);

}