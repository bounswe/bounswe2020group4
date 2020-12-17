const Customer = require("../models/customer").Customer;
const Vendor = require("../models/vendor").Vendor;

/**
 * Gets account information for a customer or vendor
 *
 * @param {
 *  userType: "customer" | "vendor",
 *  id: String
 * } params
 *
 * @returns {
 *  name: String,
 *  email: String,
 *  rating: Number,
 *  address: [String],
 *  password: String,
 *  longitude: String,
 *  latitude: String,
 *  website: String
 * } | false
 */
module.exports.getAccountInfo = async (params) => {
  let account;
  const collection = params.userType === "customer" ? Customer : Vendor;
  try {
    account = await collection.findOne({ id: params.id });

    if (account && params.userType === "customer") {
      account = account.toJSON();
      delete account._id;
      delete account.__v;
      delete account.password;
    } else if (account && params.userType === "vendor") {
      account = account.toJSON();
      delete account._id;
    }

    return account;
  } catch (error) {
    console.log(error);
    return error;
  }
};

/**
 * Performs login for vendor or customer.
 * @param {
 *  userType: "customer" | "vendor",
 *  email: String,
 *  password: String
 * } params
 *
 * @returns {userId | false}
 */
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

/**
 * Performs signup for vendor or customer.
 * @param {
 *  userType: "customer" | "vendor",
 *  email: String,
 *  password: String,
 *  longitude: String,
 *  latitude: String,
 *  website: String
 * } params
 *
 * @returns {userId | false}
 */
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
      });
    } else {
      user = await Vendor.create({
        email: params.email,
        password: params.password,
        longitude: params.longitude,
        latitude: params.latitude,
        website: params.website,
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
