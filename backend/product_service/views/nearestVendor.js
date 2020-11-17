const Vendor = require("../models/vendor").Vendor; // To use the vendor db model.

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
module.exports.getNearestVendor = async function (request, response) {
  try {
    const vendors = await Vendor.find(); // Get all vendors from the db.

    response.respond(200, "Success", {
      vendors: vendors,
    });
  } catch (error) {
    response.respond(404, "Not Found");
  }
};
