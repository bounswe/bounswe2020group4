const Customer = require("../models/customer").Customer;
const Vendor = require("../models/vendor").Vendor;
const ObjectId = require("mongoose").Types.ObjectId;

module.exports.getAccountInfo = async (params) => {
  try {
    let account;
    const collection = params.userType === "customer" ? Customer : Vendor;

    account = await collection.findOne({ _id: ObjectId(params.id) });
    account = account.toJSON();

    delete account._id;
    delete account.__v;

    return account;
  } catch (error) {
    console.log(error);
    return error;
  }
};

module.exports.updateAccountInfo = async (params) => {
  try {
    const collection = params.userType === "customer" ? Customer : Vendor;
    const account = await collection.findOne({ _id: ObjectId(params.id) });

    if (account && params.userType === "customer") {
      ["name", "email", "password", "rating", "address", "password", "gender"].forEach((field) => {
        if (params[field]) {
          account[field] = params[field];
        }
      });

      await account.save();
    } else if (account && params.userType === "vendor") {
      ["name", "email", "password", "longitude", "latitude", "website", "company"].forEach((field) => {
        if (params[field]) {
          account[field] = params[field];
        }
      });

      await account.save();
    }

    return !!account;
  } catch (error) {
    console.log(error);
    return error;
  }
};

module.exports.login = async (params) => {
  try {
    const collection = params.userType === "customer" ? Customer : Vendor;
    const user = await collection.findOne({
      email: params.email,
      password: params.password,
    });

    if (user) {
      return user._id.toString();
    }

    return false;
  } catch (error) {
    console.log(error);
    return error;
  }
};

module.exports.signup = async (params) => {
  try {
    const collection = params.userType === "customer" ? Customer : Vendor;

    let userLog = await collection.findOne({ email: params.email });
    if (userLog) {
      return "This email has been already used";
    }

    var user;
    if (params.userType === "customer") {
      user = await Customer.create({
        email: params.email,
        password: params.password,
        name: params.name,
      });
    } else {
      user = await Vendor.create({
        email: params.email,
        password: params.password,
        longitude: params.longitude,
        latitude: params.latitude,
        website: params.website,
        company: params.company,
        name: params.name,
      });
    }

    if (user) {
      return user._id.toString();
    }

    return false;
  } catch (error) {
    console.log(error);

    return error;
  }
};
