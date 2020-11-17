const mongoose = require("mongoose");
const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const Counter = require("../models/counter").Counter;
const Customer = require("../models/customer").Customer;
const fs = require("fs");

/*
 * Initialize the mongo connection.
 */

const addMockProducts = async () => {
  let clothingProducts = JSON.parse(fs.readFileSync("./mock-product-data/clothing.json"));
  const products = [];
  let idCounter = 10000;
  let vendors = [
    {
      name: "JohnsShop",
      email: "john@gmail.com",
      longitude: "41.0082° N",
      latitude: "28.9784° E",
      id: 1021,
      rating: 4.23,
    },
    {
      name: "Ahmet",
      email: "ahmet@gmail.com",
      longitude: "41.1082° N",
      latitude: "28.9284° E",
      id: 1022,
      rating: 3.22,
    },
    {
      name: "AyseTeyze",
      email: "ayse.teyze@gmail.com",
      longitude: "41.3082° N",
      latitude: "28.9484° E",
      id: 1023,
      rating: 3.21,
    },
    {
      name: "Pablos",
      email: "pablos@gmail.com",
      longitude: "41.6082° N",
      latitude: "28.9184° E",
      id: 1024,
      rating: 2.43,
    },
  ];

  await Vendor.collection.insertMany(vendors);

  await Object.keys(clothingProducts).forEach((category) => {
    clothingProducts[category].forEach((product) => {
      let sizes = [];
      const colors = ["Red", "Blue", "White", "Purple", "Orange", "Black", "Grey", "Green"];
      const brands = ["BUYO", "Nike", "Adidas", "Hotic", "Jack & Jones", "Watsons", "Defacto", "Koton"];
      if (product.category.includes("Ayakkabi")) {
        sizes = [34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44];
      } else if (product.category.includes("Giyim")) {
        sizes = ["XS", "S", "M", "L", "XL", "XXL"];
      } else {
        sizes = [];
      }

      let dbProduct = {
        name: product.name,
        id: idCounter,
        imageUrl: product.image_url,
        category: product.category,
        rating: (Math.random() * 5).toFixed(2),
        price: product.price.TRY,
        originalPrice: product.original_price.TRY,
        brand: brands[Math.floor(Math.random() * brands.length)],
        sizes: sizes.length
          ? sizes.slice(Math.floor(Math.random() * 2), Math.floor(Math.random() * sizes.length - 2) + 2)
          : undefined,
        colors: colors.slice(Math.floor(Math.random() * 2), Math.floor(Math.random() * colors.length - 2) + 3),
        vendorId: vendors[Math.floor(Math.random() * 4)].id,
      };

      dbProduct.stockValue = {};

      (dbProduct.sizes || ["None"]).forEach(function (size) {
        dbProduct.colors.forEach(function (color) {
          dbProduct.stockValue[size === "None" ? color : size + "-" + color] = Math.floor(Math.random() * 100);
        });
      });
      idCounter++;
      products.push(dbProduct);
    });
  });
  await Counter.create({
    customerCounter: 0,
    vendorCounter: 0,
    productCounter: idCounter,
  });
  await Product.collection.insertMany(products);
};

module.exports.initialize = async function () {
  try {
    await mongoose.connect(process.env.MONGO_URL);

    // await Product.deleteMany();
    // await addMockProducts();
  } catch (err) {
    console.log(err);
  }
};
