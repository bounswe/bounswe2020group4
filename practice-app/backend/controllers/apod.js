const Response = require('../utils/response');
const Request = require('../utils/request');

/**
 * Get the astronomy picture of the day from NASA api.
 * Response: {
 *    status: {
 *        success: true,
 *        code: 200
 *    }, data: {
 *        apodURL: "https://apod.nasa.gov/apod/image/1707/M63-HST-Subaru-S1024.jpg",
 *        apodTitle: "Messier 63: The Sunflower Galaxy"
 *    }
 *}
 */

 module.exports.getPictureOfToday = function (request, response) {
     try {
         Request.send({ // Send request to the NASA api.
             url: 'https://api.nasa.gov/planetary/apod?api_key=' + process.env.NASA_API_KEY,
             method: 'GET',
             body: {}
         }, async function (err, res, body) {
             if (err || body.error) { // If there is an error respond with the error.
                 return Response.handleError(response, err || body.error);
             }

             console.log("Your response's body: ", body);

             // Set the response body.
             const responseBody = {
                apodURL: body.url,
                apodTitle: body.title,
             }
             console.log("responseBody:", responseBody);

             // Respond to the front-end.
             Response.send(response, responseBody);
         });
     } catch (error) {
         Response.handleError(response, error);
     }
 };
