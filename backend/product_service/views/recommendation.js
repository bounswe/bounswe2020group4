const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const Comment = require("../models/comment").Comment;
const Customer = require("../models/customer").Customer;
const ObjectId = require("mongoose").Types.ObjectId;
const Like = require("../models/like").Like;
const { ErrorMessage } = require("../constants/error");

/**
 * Gets products within a specific category or with a search parameter
 * Filters and sort them with certain parameters and returns the result.
 *
 * @param {
 *  categories: [String],
 *  search: String,
 *  sortingFactor: String,
 *  sortingType: descending | ascending
 *  color: String,
 *  brand: String
 * } params
 *
 * @returns {[
 *  Product
 * ]}
 */
module.exports.getProducts = async (params) => {
  try {
    let products;

    if (!params.type || !params.userId) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }

    let usersAlsoProducts = new Set();
    let mostProducts;
    const productLikes = {};
    const userLikes = {};

    if (params.type === "liked") {
      const likes = await Likes.find({});

      likes.forEach((like) => {
        like = like.toJSON();

        productLikes[like.productId.toString()] = productLikes[like.productId.toString()] || {};
        productLikes[like.productId.toString()][like.userId.toString()] = true;
        userLikes[like.userId.toString()] = userLikes[like.userId.toString()] || {};
        userLikes[like.userId.toString()][like.productId.toString()] = true;
      });

      Object.keys(userLikes[params.userId] || {}).forEach((productId) => {
        Object.keys(productLikes[productId] || {}).forEach((userId) => {
          Object.keys(userLikes[userId] || {}).forEach((alsoProductId) => {
            if (alsoProductId !== productId) {
              usersAlsoProducts.add(alsoProductId);
            }
          });
        });
      });

      mostProducts = Object.keys(productLikes);
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

    return { productList: products };
  } catch (error) {
    console.log(error);
    return error;
  }
};
