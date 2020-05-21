const nearbyVendors = require('./controllers/nearestVendor');
const covid19TR = require('./controllers/covid19');
const weatherDays = require('./controllers/weatherNextDays');
const randomPlaylists = require('./controllers/randomCoronaPlaylist');
const quoteOfTheDay = require('./controllers/quoteOfTheDay');
const covidNews = require('./controllers/covidNews');
const tryToUsd = require('./controllers/tryusd');
const apod = require('./controllers/apod');
const nearestHospital = require("./controllers/nearestHospital")
const suggestBook = require('./controllers/suggestBook')
const weatherImportantCities = require('./controllers/weatherImportantCities');

const path = require('path');

// Initialize the routes.
module.exports.initialize = function (app) {
    // Redirect the initial webpage to our
    app.get('/',function(req,res) {
        res.sendFile(path.join(__dirname, '../frontend', 'index.html'));
    });
    // Redirect the GET /vendor/nearest request to the getNearestVendor function.
    app.get("/vendor/nearest", nearbyVendors.getNearestVendor);
    app.get("/covid19", covid19TR.getCovid19tr);
    // Redirect the GET /weather/daily request to the weatherNextDays function.
    app.get("/weather/daily", weatherDays.weatherNextDays);
    // Redirect the GET /randomPlaylist to the randomCoronaPlaylist function.
    app.get("/random-playlist", randomPlaylists.getRandomCoronaPlaylist);
    // Redirect GET /quote request to getQuote function
    app.get("/quote", quoteOfTheDay.getQuote);
    // Redirect the GET /covid/news request to the getNews function.
    app.get("/covid/news", covidNews.getNews);
    app.get("/tryusd", tryToUsd.getTryUsd);
    // Redirect the GET /apod request to the getPictureOfToday function.
    app.get("/apod", apod.getPictureOfToday);
    // Redirect the GET /apod request to the nearestHospital function.
    app.get("/nearesthospitals", nearestHospital.nearestHospitals);
    app.post("/suggestBook", suggestBook.suggestBook);
    // Redirect the GET /weather/importantCities request to the getCurrentWeathers function.
    app.get("/weather-important-cities", weatherImportantCities.getCurrentWeathers);
}
