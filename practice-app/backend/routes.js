const nearbyVendors = require('./controllers/nearestVendor');
const randomPlaylists = require('./controllers/randomCoronaPlaylist');

// Initialize the routes.
module.exports.initialize = function (app) {
    // Redirect the GET /vendor/nearest request to the getNearestVendor function.
    app.get("/vendor/nearest", nearbyVendors.getNearestVendor);
    // Redirect the GET /randomPlaylist to the randomCoronaPlaylist function.
    app.get("/random-playlist", randomPlaylists.getRandomCoronaPlaylist);
}