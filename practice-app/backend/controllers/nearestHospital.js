const Response = require("../utils/response");
const NearBySearch = require("googleplaces");

/**
 * Get the list of nearest hospitals to a location. Accepts latitude and longitude and optionally radius in meters.
 *
 * WARNING: due to google api restrictions, only 1000 request per 24 hour can be made.
 * 
 * Example Request:
 * Bogazici Uni: {"lat": 41.0862, "long": 29.0444, "radius": 1000 }
 * Besiktas: {"lat": 41.0422, "long": 29.0067 }
 * Malatya AVM: {"lat": 38.3482, "long": 38.3482, "radius": 5000 }
 *
 *
 * Response: {
 *    status: {
 *        success: true,
 *        code: 200
 *    }, data: {
 *        count: 9,
 *        names: ["Bahçeşehir University Health Center",
 *          "T. C. The Ministry of Health Sinanpaşa Family Health Center",
 *          "İstanbul Medical Group"
 *        ]
 *    }
 *}
 */

module.exports.nearestHospital = async function (request, response) {
  try {
    const nearBySearch = new NearBySearch(
      process.env.GOOGLE_PLACES_API_KEY,
      process.env.GOOGLE_PLACES_OUTPUT_FORMAT
    );
    const parameters = {
      location: [request.body.lat, request.body.long],
      radius: request.body.radius || 1000,
      keyword: "health, medical center"
    };
    nearBySearch.placeSearch(parameters, function (error, _response) {
      if (error) throw error;
      const responseBody = {
        count: _response.results.length,
        names: _response.results.map((r) => r.name)
      };
      Response.send(response, responseBody);
    });
  } catch (error) {
    Response.handleError(response, error);
  }
};
