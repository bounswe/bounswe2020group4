const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const Comment = require("../models/comment").Comment;
const Customer = require("../models/customer").Customer;

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

      let comments = await Comment.find({ productId: product.id });
      comments = await Promise.all(
        comments.map(async (comment) => {
          comment = comment.toJSON();
          const user = (await Customer.findOne({ id: comment.userId })).toJSON();

          return {
            id: comment._id.toString(),
            text: comment.text,
            owner: {
              username: user.name,
              email: user.email,
            },
          };
        })
      );

      const vendor = await Vendor.findOne({ id: product.vendorId });

      product.vendor = {
        name: vendor.name,
        rating: vendor.rating,
      };

      product.comments = comments;

      delete product._id;
      delete product.vendorId;
    }

    return product;
  } catch (error) {
    console.log(error);
    return error;
  }
};
