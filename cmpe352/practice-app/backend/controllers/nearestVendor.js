const Geopoint = require('geopoint'); // Library to find distance between to geo-locations
const Vendor = require('../models/vendor').Vendor; // To use the vendor db model.
const Response = require('../utils/response');
const Request = require('../utils/request');

/**
 * Get the user location from googleapi and vendor objects from the db.
 * Calcuate distances between vendors and the user and respond with the
 * nearest vendor.
 * Response: {
 *    status: {
 *        success: true,
 *        code: 200
 *    }, data: {
 *        vendor: {
 *            name: 'John',
 *            location: {
 *                latitude: 32.23,
 *                langitude: 38.21
 *            }
 *        },
 *        distance: 32811.13
 *    }
 *}
 */
module.exports.getNearestVendor = function (request, response) {
    try {
        Request.send({ // Send request to the google api.
            url: 'https://www.googleapis.com/geolocation/v1/geolocate?key=' + process.env.GOOGLE_API_KEY,
            method: 'POST',
            body: {}
        }, async function (err, res, body) {
            if (err || body.error) { // If there is an error respond with the error.
                return Response.handleError(response, err || body.error);
            }

            const vendors = await Vendor.find(); // Get all vendors from the db.
            const userLocation = body.location; // Get the user location from the response.
            let nearestVendor;
            let smallestDistance;

            // Iterate through all the vendors and find the nearest vendor.
            vendors.forEach(function (vendor) {
                // Get the distance between two locations.
                const distance = (new Geopoint(vendor.location.latitude, vendor.location.longitude))
                    .distanceTo(new Geopoint(userLocation.lat, userLocation.lng), true);
                if (!nearestVendor) {
                    nearestVendor = vendor;
                    smallestDistance = distance;
                } else if (distance < smallestDistance) {
                    smallestDistance = distance;
                    nearestVendor = vendor;
                }
            });

            // Set the response body.
            const responseBody = nearestVendor ? {
                vendor: {
                    name: nearestVendor.name,
                    location: nearestVendor.location,
                },
                distance: smallestDistance
            } : {};

            // Respond to the front-end.
            Response.send(response, responseBody);
        });
    } catch (error) {
        Response.handleError(response, error);
    }
};