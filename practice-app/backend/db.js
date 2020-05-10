const mongoose = require('mongoose');
const Vendor = require('./models/vendor').Vendor;
const Constants = require('./constants');

/**
 * Insert dummy documents to the database if it's empty.
 */
var insertDummyDocuments = async function () {
    await Vendor.deleteMany(); // TODO: It's for test purposes, remove it after db is stable.
    var numberOfVendersInDB = await Vendor.countDocuments();

    if (numberOfVendersInDB === 0) {
        // Create vendor objects
        const vendors = [
            new Vendor({
                name: 'Jack',
                location: {
                    latitude: 37.23,
                    longitude: 32.21
                }
            }),
            new Vendor({
                name: 'London',
                location: {
                    latitude: 32.23,
                    longitude: 38.21
                }
            }),
            new Vendor({
                name: 'White',
                location: {
                    latitude: 32.23,
                    longitude: 22.21
                }
            })
        ];

        // Save vendors to the db
        vendors.forEach(async function (vendor) {
            await vendor.save();
        });
    }


};

/* 
 * Initialize the mongo connection.
 */
module.exports.initialize = async function () {
    await mongoose.connect(Constants.MONGO_URL);

    await insertDummyDocuments();
}