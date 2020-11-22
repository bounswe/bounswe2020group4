const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;

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
    let categories = [];

    rawResult[0].distinctCategories.forEach((distinctCategory) => {
      let temp = categories;
      let index = 1;
      distinctCategory.forEach((category) => {
        if (!temp.some((subcategory) => subcategory.name === category)) {
          temp.push({
            name: category,
            subcategories: [],
            path: distinctCategory.slice(0, index).join(","),
          });
        }

        temp = temp.find((subcategory) => subcategory.name === category).subcategories;

        index++;
      });
    });

    return categories;
  } catch (error) {
    console.log(error);
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

    products = await Promise.all(
      products.map(async (product) => {
        product = product.toJSON();

        const vendor = await Vendor.findOne({ id: product.vendorId });

        product.vendor = {
          name: vendor.name,
          rating: vendor.rating,
        };

        delete product._id;
        delete product.vendorId;

        return product;
      })
    );

    return products;
  } catch (error) {
    console.log(error);
    return error;
  }
};

module.exports.getProduct = async (params) => {
  try {
    let product;

    if (params.id) {
      product = await Product.findOne({ id: params.id });
    }

    if (product) {
      product = product.toJSON();

      const vendor = await Vendor.findOne({ id: product.vendorId });

      product.vendor = {
        name: vendor.name,
        rating: vendor.rating,
      };

      delete product._id;
      delete product.vendorId;
    }

    return product;
  } catch (error) {
    console.log(error);
    return error;
  }
};
