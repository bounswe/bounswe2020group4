const Response = require('../utils/response');
const Request = require('../utils/request');
/**
 *  Latest TRY/USD rate using https://www.exchangerate-api.com/
 * Response: {
 *    status: {
 *        success: true,
 *        code: 200
 *    }, data: {

          TRY: 2,4344
 *    }
 *}
 */
module.exports.getTryUsd = function (request, response) {
    try {
        Request.send({ // Send request to the google api.
            url: 'https://prime.exchangerate-api.com/v5/' + process.env.TRY_USD_API_KEY + '/latest/USD',
            method: 'POST',
            body: {}
        }, async function (err, res, body) {
            if (err || body.error) { // If there is an error respond with the error.
                return Response.handleError(response, err || body.error);
            }

            var myResponseParam = body.conversion_rates.TRY;

            // Set the response body.
            const responseBody = {

                TRY: myResponseParam

            }


            // Respond to the front-end.
            Response.send(response, responseBody);
        });
    } catch (error) {
        Response.handleError(response, error);
    }
};