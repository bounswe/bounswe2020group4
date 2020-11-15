const Product = require("../models/product").Product;

/**
 * Get the user location from googleapi and vendor objects from the db.
 * Calcuate distances between vendors and the user and respond with the
 * nearest vendor.
 * Response: {
 *    status: {
 *        success: true,
 *        code: 200
 *    }, data: {
 *        vendor: {
 *            name: 'John',
 *            location: {
 *                latitude: 32.23,
 *                langitude: 38.21
 *            }
 *        },
 *        distance: 32811.13
 *    }
 *}
 */
module.exports.getProductCategories = async () => {
  try {
    const rawResult = await Product.aggregate([
      {
        $group: {
          _id: 0,
          distinctCategories: { $addToSet: "$category" },
        },
      },
    ]);
    let categories = {};
    rawResult[0].distinctCategories.forEach((distinctCategory) => {
      let temp = categories;
      distinctCategory.forEach((category) => {
        temp[category] = temp[category] || {
          subcategories: {},
        };
        temp = temp[category].subcategories;
      });
    });

    return categories;
  } catch (error) {
    return error;
  }
};

module.exports.getProducts = async (params) => {
  try {
    let products;

    if (params.categories) {
      products = await Product.find({ category: { $all: JSON.parse(params.categories) } });
    } else if (params.search) {
      products = await Product.find({ name: { $regex: params.search, $options: "i" } });
    }

    return products;
  } catch (error) {
    console.log(error);
    return error;
  }
};
