const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const Comment = require("../models/comment").Comment;
const Customer = require("../models/customer").Customer;
const ObjectId = require("mongoose").Types.ObjectId;

module.exports.addProduct = async (query) => {
  try {
    console.log(query);
  } catch (error) {
    console.log(error);
    return error;
  }
};
