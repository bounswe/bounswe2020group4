const Customer = require("../models/customer").Customer;
const Vendor = require("../models/vendor").Vendor;
const Counter = require("../models/counter").Counter;

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

    } else if(account && params.userType === "vendor"){

      account = account.toJSON();
      delete account._id;

    }

    return account;
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
      return user.id;
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

    const counter = await Counter.findOne();
    counter[params.userType + "Counter"]++;
    await counter.save();

    const user = await collection.create({
      email: params.email,
      password: params.password,
      id: counter[params.userType + "Counter"],
    });

    if (user) {
      return user.id;
    }

    return false;
  } catch (error) {
    console.log(error);

    return error;
  }
};
