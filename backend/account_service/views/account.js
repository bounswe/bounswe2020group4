const Customer = require("../models/customer").Customer;
const Vendor = require("../models/vendor").Vendor;
const ObjectId = require("mongoose").Types.ObjectId;
const { ErrorMessage } = require("../constants/error");
const nodemailer = require("nodemailer");

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
    if (!params.id || !params.address) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }

    params.address = JSON.parse(params.address);
    const account = await Customer.findOne({ _id: ObjectId(params.id) });
    if (account) {
      if (account.address.some((addr) => addr.addressTitle === params.address.addressTitle)) {
        return { success: false, message: ErrorMessage.ADDRESS_ALREADY_EXISTS };
      }
      account.address.push(params.address);
      await account.save();

      return { success: true, address: account.address };
    }

    return { success: false, message: ErrorMessage.USER_NOT_FOUND };
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
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
    if (!params.id || !params.address) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }

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
      return { success: true, address: account.address };
    }

    return { success: false, message: ErrorMessage.USER_NOT_FOUND };
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
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
    if (!params.id || !params.address) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }

    const account = await Customer.findOne({ _id: ObjectId(params.id) });
    params.address = JSON.parse(params.address);
    if (account) {
      if (!account.address.some((addr) => addr.addressTitle === params.address.addressTitle)) {
        return "Address not found.";
      } else {
        account.address = account.address.filter((addr) => addr.addressTitle !== params.address.addressTitle);
      }
      await account.save();

      return { success: true, address: account.address };
    }

    return { success: false, message: ErrorMessage.USER_NOT_FOUND };
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
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
    if (!params.id || !params.userType) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }

    const collection = params.userType === "customer" ? Customer : Vendor;
    let account = await collection.findOne({ _id: ObjectId(params.id) });

    if (!account) {
      return { success: false, message: ErrorMessage.USER_NOT_FOUND };
    }

    account = account.toJSON();

    delete account._id;
    delete account.__v;

    return { success: true, account };
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
  }
};
/**
 * Gets the account id and fields, updates the account with that id
 *
 * @param {
 * id: String,
 * userType: String,
 * name: String,
 * surname: String,
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
    if (!params.id || !params.userType) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }

    const collection = params.userType === "customer" ? Customer : Vendor;
    const account = await collection.findOne({ _id: ObjectId(params.id) });
    if (account && params.userType === "customer") {
      ["name", "email", "gender", "phoneNumber"].forEach((field) => {
        if (params[field]) {
          if (field !== "name") {
            account[field] = params[field];
          } else {
            account["name"] = params["name"] + " " + params["surname"];
          }
        }
      });
      await account.save();
    } else if (account && params.userType === "vendor") {
      ["name", "email", "longitude", "latitude", "website", "company"].forEach((field) => {
        if (params[field]) {
          account[field] = params[field];
        }
      });
      await account.save();
    }

    if (account) {
      return { success: true };
    }

    return { success: false, message: ErrorMessage.USER_NOT_FOUND };
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
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
    if (!params.id || !params.userType) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }
    const collection = params.userType === "customer" ? Customer : Vendor;
    const account = await collection.findOne({ _id: ObjectId(params.id) });
    if (account) {
      account.password = params.password;
      await account.save();

      return { success: true };
    }
    return { success: false, message: ErrorMessage.USER_NOT_FOUND };
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
  }
};

/**
 * Gets the account id and updates changes the password of the account of that id
 *
 * @param {
 * email: String
 * } params
 * @return {
 *  Boolean: Account exists
 * }
 */
module.exports.forgotPassword = async (params) => {
  try {
    if (!params.email) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }
    var account = await Customer.findOne({ email: params.email });
    var userType = "customer";
    if (!account) {
      account = await Vendor.findOne({ email: params.email });
      userType = "vendor";
    }
    if (account) {
      sendForgotPasswordEmail(params.email, account._id.toString(), userType);
      return { success: true };
    }
    return { success: false, message: ErrorMessage.USER_NOT_FOUND };
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
  }
};

/**
 * Gets the account id and updates the status of the account of that id
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
module.exports.verifyAccount = async (params) => {
  try {
    if (!params.id || !params.userType) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }
    const collection = params.userType === "customer" ? Customer : Vendor;
    const account = await collection.findOne({ _id: ObjectId(params.id) });
    if (account) {
      account.status = "verified";
      await account.save();

      return { success: true };
    }
    return { success: false, message: ErrorMessage.USER_NOT_FOUND };
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
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
 * @returns {userId | false | userStatus}
 */
module.exports.login = async (params) => {
  try {
    if (!params.email || !params.password || !params.userType) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }
    const collection = params.userType === "customer" ? Customer : Vendor;
    const user = await collection.findOne({
      email: params.email,
      password: params.password,
    });
    if (user) {
      return { success: true, userId: user._id.toString(), userStatus: user.status };
    }
    return { success: false, message: ErrorMessage.USER_NOT_FOUND };
  } catch (error) {
    return { success: false, message: error.message || error };
  }
};

function sendVerificationMail(userEmail, userType, userId) {
  var transporter = nodemailer.createTransport({
    service: "Gmail",
    auth: {
      user: "buyoboun@gmail.com",
      pass: "buyo1234",
    },
  });
  var mailOptions = {
    from: '"BUYO" <buyoboun@gmail.com>',
    to: userEmail,
    subject: "Your BUYO verification e-mail",
    html:
      '<p>Click <a href ="http://buyomarket.ml/verifyuser?userType=' +
      userType +
      "&id=" +
      userId +
      '"> here </a> to verify your BUYO account.</p>',
  };

  transporter.sendMail(mailOptions, function (error, info) {
    if (error) {
      console.log(error);
    } else {
      console.log("Email sent: " + info.response);
    }
  });
}

function sendForgotPasswordEmail(userEmail, userId, userType) {
  var transporter = nodemailer.createTransport({
    service: "Gmail",
    auth: {
      user: "buyoboun@gmail.com",
      pass: "buyo1234",
    },
  });

  var mailOptions = {
    from: '"BUYO" <buyoboun@gmail.com>',
    to: userEmail,
    subject: "Did you forgot your password?",
    html:
      '<p>Click <a href ="http://buyomarket.ml/forgotpassword?userId=' +
      userId +
      "&userType=" +
      userType +
      '"> here </a> to reset your BUYO account password.</p>',
  };

  transporter.sendMail(mailOptions, function (error, info) {
    if (error) {
      console.log(error);
    } else {
      console.log("Email sent: " + info.response);
    }
  });
}
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
    if (!params.email || !params.password || !params.userType) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }
    const collection = params.userType === "customer" ? Customer : Vendor;
    let userLog = await collection.findOne({ email: params.email });
    if (userLog) {
      if (params.userType === "customer") {
        if (userLog.googleToken) {
          if (userLog.password) {
            return { success: false, message: ErrorMessage.EMAIL_HAS_BEEN_USED };
          }
          userLog.password = params.password;
          await userLog.save();
          return { success: true, userId: userLog._id.toString() };
        }
      }
      return { success: false, message: ErrorMessage.EMAIL_HAS_BEEN_USED };
    }
    let user;
    if (params.userType === "customer") {
      user = await Customer.create({
        email: params.email,
        password: params.password,
        name: params.name,
        status: "not-verified",
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
        status: "not-verified",
      });
    }
    if (user) {
      sendVerificationMail(params.email, params.userType, user._id.toString());
      return { success: true, userId: user._id.toString() };
    }

    return { success: false, message: ErrorMessage.COULD_NOT_CREATE_USER };
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
  }
};

module.exports.signInByGoogle = async (params) => {
  try {
    if (!!params.email || !!params.token || !!params.name) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }

    let user = await Customer.findOne({ email: params.email });
    if (!!user) {
      user = await Customer.create({
        email: params.email,
        googleToken: params.token,
        name: params.name,
        status: "verified",
      });
    } else {
      if (user.googleToken) {
        if (user.googleToken !== params.token) {
          return { success: false, message: ErrorMessage.WRONG_GOOGLE_TOKEN };
        }
      } else {
        user.googleToken = params.token;
        await user.save();
      }
    }
    return { success: true, userId: user._id.toString() };
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
  }
};


module.exports.getCustomerList = async () => {
  const customerList = await Customer.find();

  return customerList;
};