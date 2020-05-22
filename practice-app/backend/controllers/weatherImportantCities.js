const Response = require('../utils/response');
const Request = require('../utils/request');

/**
 * Get the current weather of some important cities from openweather api.
 * Response: {
 *    status: {
 *        success: true,
 *        code: 200
 *    }, data: {
 *        currentWeathers: [
 *            {
 *                cityName: 'name of the City1',
 *                currentTemp: 'current temperature for given city',
 *                description: 'A short description of current weather',
 *                iconUrl: 'https://www.example.org'
 *            },             
 *            {
 *                cityName: 'name of the City2',
 *                currentTemp: 'current temperature for given city',
 *                description: 'A short description of current weather',
 *                iconUrl: 'https://www.example2.org'
 *            },
 *            {
 *                cityName: 'name of the City3',
 *                currentTemp: 'current temperature for given city',
 *                description: 'A short description of current weather',
 *                iconUrl: 'https://www.example3.org'
 *            }
 *            ... // the number of city varies, max num = 20 it depends given ids.
 *        ]
 *    }
 *}
 */

module.exports.getCurrentWeathers = function (request, response) {
    try {
        /*
        http://bulk.openweathermap.org/sample/  you can find all indexes and ids from this website.
        group ids are ids of the cities from openweather api.
        745042 -> İstanbul
        323784 -> Ankara 
        311044 -> İzmir
        ******
        For temperature in Fahrenheit use units=imperial
        For temperature in Celsius use units=metric
        Temperature in Kelvin is used by default, no need to use units parameter in API call
        ******
        You can use lang parameter to get the output in your language.
        Translation is applied for the city name and description fields.
        lang language code
        ******
        */
        Request.send({ // Sends request to openweather api.
            url: 'http://api.openweathermap.org/data/2.5/group?id=745042,323784,311044&units=metric&lang=tr&appid=' + process.env.OPENWEATHER_API_KEY,
            method: 'GET',
            body: {}
        }, async function (err, res, body) {
            if (err || body.error) { // If there is an error respond with the error.
                return Response.handleError(response, err || body.error);
            }

            let currentWeathers = []

            body.list.forEach(function (weather) {
                currentWeathers.push({
                    cityName: weather.name,
                    currentTemp: weather.main.temp,
                    description: weather.weather[0].description,
                    /*n
                    To get weather icon url 
                    light rain icon = "10d". 
                    URL is http://openweathermap.org/img/wn/10d@2x.png
                    */
                    iconUrl: 'http://openweathermap.org/img/wn/' + weather.weather[0].icon + '@2x.png'
                })
            });

            // Set the response body.
            const responseBody = {
                currentWeathers: currentWeathers
            }

            // Respond to the front-end.
            Response.send(response, responseBody);
        });
    } catch (error) {
        Response.handleError(response, error);
    }
};