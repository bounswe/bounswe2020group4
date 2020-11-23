const Customer = require("../models/customer").Customer;
const Vendor = require("../models/vendor").Vendor;
const Counter = require("../models/counter").Counter;

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
    let userLog = await Customer.findOne({ email: params.email, userType:params.userType });
    if(!!userLog) {
      return "This email has been already used";
    }  

    const collection = params.userType === "customer" ? Customer : Vendor;
    const counter = await Counter.findOne();
    counter[params.userType + "Counter"]++;
    await counter.save();

    const user = await collection.create({
      email: params.email,
      password: params.password,
      id: counter[params.userType + "Counter"],
    });

    if (user.length > 0) {
      return user.id;
    }

    return false;
  } catch (error) {
    console.log(error);

    return error;
  }
};
