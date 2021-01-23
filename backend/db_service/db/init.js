const mongoose = require("mongoose");
const Product = require("../models/product").Product;
const Comment = require("../models/comment").Comment;
const Customer = require("../models/customer").Customer;
const Like = require("../models/like").Like;
const Message = require("../models/message").Message;
const OrderedProduct = require("../models/ordered_product").OrderedProduct;
const CartProduct = require("../models/cart_products").CartProduct;
const Vendor = require("../models/vendor").Vendor;
const fs = require("fs");

/**
 * Adds mock documents to the database from mock
 * product json files.
 */
module.exports.initializeMockDB = async () => {
  await Product.deleteMany();
  await Vendor.deleteMany();
  await Comment.deleteMany();
  await Customer.deleteMany();
  await Like.deleteMany();
  await Message.deleteMany();
  await OrderedProduct.deleteMany();
  await CartProduct.deleteMany();

  const clothingProducts = JSON.parse(fs.readFileSync("./mock-product-data/clothing.json"));
  const cosmeticProducts = JSON.parse(fs.readFileSync("./mock-product-data/cosmetic.json"));
  const electronicProducts = JSON.parse(fs.readFileSync("./mock-product-data/electronics.json"));
  const furnitureProducts = JSON.parse(fs.readFileSync("./mock-product-data/furniture.json"));
  let vendors = JSON.parse(fs.readFileSync("./mock-product-data/vendors.json"));

  const res = await Vendor.collection.insertMany(vendors);

  vendors = Object.values(res.insertedIds);

  const products = clothingProducts.concat(cosmeticProducts).concat(electronicProducts).concat(furnitureProducts);

  products.forEach((product) => (product.vendorId = vendors[Math.floor(Math.random() * vendors.length)]));

  await Product.collection.insertMany(products);
};

/**
 * Connects to the mongodb database.
 */
module.exports.initialize = async function () {
  try {
    await mongoose.connect(process.env.MONGO_URL, { useNewUrlParser: true, useUnifiedTopology: true });
  } catch (err) {
    console.log(err);
  }
};
