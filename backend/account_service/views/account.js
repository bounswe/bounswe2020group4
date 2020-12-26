const Customer = require("../models/customer").Customer;
const Vendor = require("../models/vendor").Vendor;
const ObjectId = require("mongoose").Types.ObjectId;
/**
 * Adds a new address to a customer user.
 *
 * @param {
 *  id: String
 *  address: Object
 * } params
 *
 * @returns {
 *  address: [Object],
 * }
 */
module.exports.addAddress = async (params) => {
  try {
    params.address = JSON.parse(params.address);
    const account = await Customer.findOne({ _id: ObjectId(params.id) });
    if (account) {
      if (account.address.some((addr) => addr.addressTitle === params.address.addressTitle)) {
        return "Please change your address title.";
      }
      account.address.push(params.address);
      await account.save();
    }
    return account.address;
  } catch (error) {
    console.log(error);
    return error;
  }
};
/**
 * Updates an address of a customer user with the same address title.
 *
 * @param {
 *  id: String
 *  address: Object
 * } params
 *
 * @returns {
 *  address: [Object],
 * } | false
 */
module.exports.updateAddress = async (params) => {
  try {
    const account = await Customer.findOne({ _id: ObjectId(params.id) });
    params.address = JSON.parse(params.address);
    if (account) {
      if (!account.address.some((addr) => addr.addressTitle === params.address.addressTitle)) {
        return "Address not found.";
      } else {
        account.address = account.address.filter((addr) => addr.addressTitle !== params.address.addressTitle);
      }
      account.address.push(params.address);
      await account.save();
      return account.address;
    }
  } catch (error) {
    console.log(error);
    return error;
  }
};
/**
 * Deletes the address of a customer user with the same address title.
 *
 * @param {
 *  id: String
 *  address: Object
 * } params
 *
 * @returns {
 *  address: [Object],
 * } | false
 */
module.exports.deleteAddress = async (params) => {
  try {
    const account = await Customer.findOne({ _id: ObjectId(params.id) });
    params.address = JSON.parse(params.address);
    if (account) {
      if (!account.address.some((addr) => addr.addressTitle === params.address.addressTitle)) {
        return "Address not found.";
      } else {
        account.address = account.address.filter((addr) => addr.addressTitle !== params.address.addressTitle);
      }
      await account.save();
      return account.address;
    }
  } catch (error) {
    console.log(error);
    return error;
  }
};
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
/**
 * Gets the account id and fields, updates the account with that id
 *
 * @param {
 * id: String,
 * userType: String,
 * name: String,
 * email: String,
 * rating: Number,
 * address: String,
 * gender: String
 * } params
 * @return {
 *  Boolean: Account exists
 * }
 */
module.exports.updateAccountInfo = async (params) => {
  try {
    const collection = params.userType === "customer" ? Customer : Vendor;
    const account = await collection.findOne({ _id: ObjectId(params.id) });
    if (account && params.userType === "customer") {
      ["name", "surname", "email", "rating", "gender"].forEach((field) => {
        if (params[field]) {
          account[field] = params[field];
        }
      });
      await account.save();
    } else if (account && params.userType === "vendor") {
      ["name", "surname", "email", "longitude", "latitude", "website", "company"].forEach((field) => {
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
/**
 * Gets the account id and updates changes the password of the account of that id
 *
 * @param {
 * id: String,
 * userType: String,
 * password: String
 * } params
 * @return {
 *  Boolean: Account exists
 * }
 */
module.exports.changePassword = async (params) => {
  try {
    const collection = params.userType === "customer" ? Customer : Vendor;
    const account = await collection.findOne({ _id: ObjectId(params.id) });
    if (account) {
      account.password = params.password;
      await account.save();
    }
    return !!account;
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