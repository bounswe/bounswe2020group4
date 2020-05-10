const nearbyVendors = require('./controllers/nearbyVendors');

module.exports.initialize = function(app) {
    app.get("/vendors/nearby", nearbyVendors.getNearbyVendors);
}