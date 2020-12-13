const mongoose = require("mongoose");
const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const fs = require("fs");

/*
 * Initialize the mongo connection.
 */

module.exports.initializeMockDB = async () => {
  await Product.deleteMany();
  await Vendor.deleteMany();

  const clothingProducts = JSON.parse(fs.readFileSync("./mock-product-data/clothing.json"));
  const cosmeticProducts = JSON.parse(fs.readFileSync("./mock-product-data/cosmetic.json"));
  const electronicProducts = JSON.parse(fs.readFileSync("./mock-product-data/electronic.json"));
  let vendors = JSON.parse(fs.readFileSync("./mock-product-data/vendors.json"));

  const res = await Vendor.collection.insertMany(vendors);

  vendors = Object.values(res.insertedIds);

  const products = clothingProducts.concat(cosmeticProducts).concat(electronicProducts);

  products.forEach((product) => (product.vendorId = vendors[Math.floor(Math.random() * vendors.length)]));

  await Product.collection.insertMany(products);
};

module.exports.initialize = async function () {
  try {
    await mongoose.connect(process.env.MONGO_URL, { useNewUrlParser: true, useUnifiedTopology: true });
  } catch (err) {
    console.log(err);
  }
};
