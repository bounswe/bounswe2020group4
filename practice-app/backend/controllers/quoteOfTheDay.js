const Response = require('../utils/response');
const Request = require('../utils/request');

/**
 * Get a random quote from the quote API.
 * Response: {
 *    status: {
 *        success: true,
 *        code: 200
 *    }, data: {
 *			quote: '...',
 *			author: '...'
 *    }
 *}
 */

module.exports.getQuote = async function (request, response) {
    try {
        Request.send({ //
            url: 'https://api.forismatic.com/api/1.0/?method=getQuote&lang=en&format=jsonp&jsonp=?',
            method: 'POST',
            body: {}
        }, async function (err, res, body) {
            if (err || body.error) { // If there is an error respond with the error.
                return Response.handleError(response, err || body.error);
            }
            //console.log("Response body: ", body);

            body = body.substring(2, body.length - 1)
            bodyJson = JSON.parse(body)

            // Set the response body.
            const responseBody = {
                quote: bodyJson["quoteText"],
                author: bodyJson["quoteAuthor"]
            }

            // Respond to the front-end.
            Response.send(response, responseBody);
        });
    } catch (error) {
        Response.handleError(response, error);
    }
};
