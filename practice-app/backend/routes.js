const nearbyVendors = require('./controllers/nearestVendor');
const covidNews = require('./controllers/covidNews');

// Initialize the routes.
module.exports.initialize = function (app) {
    // Redirect the GET /vendor/nearest request to the getNearestVendor function.
    app.get("/vendor/nearest", nearbyVendors.getNearestVendor);
}

module.exports.initialize = function (app) {
    // Redirect the GET /vendor/nearest request to the getNearestVendor function.
    app.get("/covid/news", covidNews.getNews);
}

