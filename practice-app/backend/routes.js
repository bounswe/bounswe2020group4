const nearbyVendors = require('./controllers/nearestVendor');
const covid19TR = require('./controllers/covid19');
const weatherDays = require('./controllers/weatherNextDays');
const randomPlaylists = require('./controllers/randomCoronaPlaylist');

// Initialize the routes.
module.exports.initialize = function (app) {
    // Redirect the GET /vendor/nearest request to the getNearestVendor function.
    app.get("/vendor/nearest", nearbyVendors.getNearestVendor);
    app.get("/covid19", covid19TR.getCovid19tr);
    // Redirect the GET /weather/daily request to the weatherNextDays function.
    app.get("/weather/daily", weatherDays.weatherNextDays);
    // Redirect the GET /randomPlaylist to the randomCoronaPlaylist function.
    app.get("/random-playlist", randomPlaylists.getRandomCoronaPlaylist);
}
