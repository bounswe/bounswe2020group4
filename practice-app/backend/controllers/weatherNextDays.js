const Response = require('../utils/response');
const Request = require('../utils/request');
/**
 * Get the weather of Istanbul for the next 8 days from
 * openweather api.
 * Response: {
 *    status: {
 *        success: true,
 *        code: 200
 *    }, data: {
 *        day1: {
 *            minTemp: 18,
 *            maxTemp: 26,
 *            desc: 'broken clouds'
 *        },
 *        day2: {
 *            minTemp: 17,
 *            maxTemp: 27,
 *            desc: 'broken clouds'
 *        },
 *        day3: {
 *            minTemp: 19,
 *            maxTemp: 26,
 *            desc: 'overcast clouds'
 *        },
 *        day4: {
 *            minTemp: 16,
 *            maxTemp: 20,
 *            desc: 'light rain'
 *        },
 *        day5: {
 *            minTemp: 15,
 *            maxTemp: 19,
 *            desc: 'broken clouds'
 *        },
 *        day6: {
 *            minTemp: 14,
 *            maxTemp: 18,
 *            desc: 'broken clouds'
 *        },
 *        day7: {
 *            minTemp: 13,
 *            maxTemp: 18,
 *            desc: 'clear sky'
 *        },
 *    }
 *}
 */
module.exports.weatherNextDays = function (request, response) {
    try {
        Request.send({ // Sends request to openweather api
            url: 'https://api.openweathermap.org/data/2.5/onecall?lat=41.008240&lon=28.978359&units=metric&exclude=current,minutely,hourly&appid=' + process.env.OPENWEATHER_API_KEY,
            method: 'GET',
            body: {}
        }, async function (err, res, body) {
            if (err || body.error) { // If there is an error respond with the error.
                return Response.handleError(response, err || body.error);
            }

            var dailyInfo = body.daily; // Get the data of all days from response
            // Construct the response body
            const responseBody = {
                day1: {
                    minTemp: Math.round(dailyInfo[0].temp.min),
                    maxTemp: Math.round(dailyInfo[0].temp.max),
                    desc: dailyInfo[0].weather[0].description,
                },
                day2: {
                    minTemp: Math.round(dailyInfo[1].temp.min),
                    maxTemp: Math.round(dailyInfo[1].temp.max),
                    desc: dailyInfo[1].weather[0].description,
                },
                day3: {
                    minTemp: Math.round(dailyInfo[2].temp.min),
                    maxTemp: Math.round(dailyInfo[2].temp.max),
                    desc: dailyInfo[2].weather[0].description,
                },
                day4: {
                    minTemp: Math.round(dailyInfo[3].temp.min),
                    maxTemp: Math.round(dailyInfo[3].temp.max),
                    desc: dailyInfo[3].weather[0].description,
                },
                day5: {
                    minTemp: Math.round(dailyInfo[4].temp.min),
                    maxTemp: Math.round(dailyInfo[4].temp.max),
                    desc: dailyInfo[4].weather[0].description,
                },
                day6: {
                    minTemp: Math.round(dailyInfo[5].temp.min),
                    maxTemp: Math.round(dailyInfo[5].temp.max),
                    desc: dailyInfo[5].weather[0].description,
                },
                day7: {
                    minTemp: Math.round(dailyInfo[6].temp.min),
                    maxTemp: Math.round(dailyInfo[6].temp.max),
                    desc: dailyInfo[6].weather[0].description,
                }
            };
            // Send the respond to the front-end
            Response.send(response, responseBody);
        });
    } catch (error) {
        Response.handleError(response, error);
    }
};