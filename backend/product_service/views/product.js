const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const Comment = require("../models/comment").Comment;
const Customer = require("../models/customer").Customer;
const ObjectId = require("mongoose").Types.ObjectId;

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

    if(!!params.sortingFactor){
      try{
        products = products.sort((product1, product2) => (params.sortingType =="descending" ? -1 : 1)*(product1[params.sortingFactor]-product2[params.sortingFactor]));
      } catch{
        console.log("Check your sorting factor") // No need to return this value. I put it here for debugging.
      }
    }

    if (!!params.subcategory) {
      products = products.filter(function (product) {
        return product.category.indexOf(params.subcategory) > -1
      })
    }

    if (!!params.color) {
      products = products.filter(function (product) {
        return product.colors.indexOf(params.color) > -1
      })
    }

    if (!!params.size) {
      products = products.filter(function (product) {
        return product.sizes.indexOf(params.size) > -1
      })
    }

    if (!!params.brand) {
      products = products.filter(function (product) {
        return product.brand.indexOf(params.brand) > -1
      })
    }

    products = await Promise.all(
      products.map(async (product) => {
        product = product.toJSON();

        const vendor = await Vendor.findById(product.vendorId);

        product.vendor = {
          name: vendor.name,
          rating: vendor.rating,
        };
        product.id = product._id.toString();

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
      product = await Product.findOne({ _id: ObjectId(params.id) });
    }

    if (product) {
      product = product.toJSON();

      let comments = await Comment.find({ productId: ObjectId(product._id) });
      comments = await Promise.all(
        comments.map(async (comment) => {
          comment = comment.toJSON();
          const user = (await Customer.findOne({ _id: comment.userId })).toJSON();

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

      const vendor = await Vendor.findOne({ _id: product.vendorId });

      product.vendor = {
        name: vendor.name,
        rating: vendor.rating,
      };
      product.id = product._id.toString();

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
