const Response = require('../utils/response');
const Request = require('../utils/request');

/**
 * Get the lastest update on the numbers of Coronovirus cases in Turkey,
 * using 'covid19api.com'
 *
 * Response: {
    "status": {
        "success": true,
        "code": 200
    },
    "error": false,
    "data": {
        "NewConfirmed": 1114,
        "TotalConfirmed": 139771,
        "NewDeaths": 55,
        "TotalDeaths": 3841,
        "NewRecovered": 3089,
        "TotalRecovered": 95780,
        "ActiveCases": 40150,
        "lastUpdate": "2020-05-12"
    }
}
 */
module.exports.getCovid19tr = function (request, response) {
    try {
        Request.send({ // Send request to the covid19 api.
            'method': 'GET',
            'url': 'https://api.covid19api.com/summary',
            'headers': {}
        }, async function (err, res, body) {
            if (err || body.error) { // If there is an error respond with the error.
                return Response.handleError(response, err || body.error);
            }

            try { // Try if the maximum request limit is not reached.
                TRindex = 0;
                for (i = 0; i < body.Countries.length; i++) {
                    if (body.Countries[i].CountryCode === "TR") {
                        TRindex = i;
                    }
                }

                var dataTR = (body.Countries)[TRindex]

                // Set the response body.
                const responseBody = dataTR.CountryCode === "TR" ? {
                    NewConfirmed: dataTR.NewConfirmed,
                    TotalConfirmed: dataTR.TotalConfirmed,
                    NewDeaths: dataTR.NewDeaths,
                    TotalDeaths: dataTR.TotalDeaths,
                    NewRecovered: dataTR.NewRecovered,
                    TotalRecovered: dataTR.TotalRecovered,
                    ActiveCases: dataTR.TotalConfirmed - (dataTR.TotalRecovered + dataTR.TotalDeaths),
                    lastUpdate: dataTR.Date.substring(0, 10)
                } : {};

                // Respond to the front-end.
                Response.send(response, responseBody);

            } catch (error) { // If the maximum number of request reached, return message.
                // Set the response body.
                const responseBody = {
                    Message: body + "Please, try again."
                };

                // Respond to the front-end.
                Response.send(response, responseBody);

            }

        });
    } catch (error) {
        Response.handleError(response, error);
    }
};
