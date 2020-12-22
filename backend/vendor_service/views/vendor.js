const Product = require("../models/product").Product;
const ObjectId = require("mongoose").Types.ObjectId;

module.exports.addProducts = async (products) => {
  try {
    const dbProducts = [];

    products.forEach((product) => {
      const product = new Product({
        name: product.name,
        imageUrl: product.imageUrl,
        category: product.category,
        numberOfRatings: 0,
        price: product.price,
        originalPrice: product.originalPrice || product.price,
        stockValue: product.stockValue,
        brand: product.brand,
        sizes: product.sizes,
        colors: product.colors,
        vendorId: ObjectId(product.vendorId),
      });

      dbProducts.push(product);
    });

    await Promise.all(dbProducts.map(async (dbProduct) => await dbProduct.save()));

    return dbProducts.map((dbProduct) => dbProduct._id.toString());
  } catch (error) {
    console.log(error);
    return error;
  }
};
