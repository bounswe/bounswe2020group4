const mongoose = require("mongoose");
const Product = require("../models/product").Product;
const fs = require("fs");

/*
 * Initialize the mongo connection.
 */

const addMockProducts = async () => {
  let clothingProducts = JSON.parse(fs.readFileSync("./mock-product-data/clothing.json"));
  const products = [];
  let idCounter = 10000;

  Object.keys(clothingProducts).forEach((category) => {
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
  await Product.collection.insertMany(products);
};

module.exports.initialize = async function () {
  try {
    await mongoose.connect(process.env.MONGO_URL);
  } catch (err) {
    console.log(err);
  }
};
