const Customer = require("../models/customer").Customer;
const ObjectId = require("mongoose").Types.ObjectId;

module.exports.getCustomerAddresses = async (params) => {
    try {
        let customer;

        if (params.id) {
            customer = await Customer.findOne({ _id: ObjectId(params.id) });
        }

        if (customer) {
            customer = customer.toJSON();

            return customer.address;
        }
        return false;

    } catch (error) {
        return error;
    }
};

module.exports.addCustomerAddress = async (params) => {
    try {
        if (params.id && params.address) {
            const isUpdated = await Customer.update({ _id: ObjectId(params.id) }, { $push: { address: params.address }});

            if (isUpdated) {
                return true;
            }
            else {
                return false;
            }
        }
    } catch {
        return error;
    }
};