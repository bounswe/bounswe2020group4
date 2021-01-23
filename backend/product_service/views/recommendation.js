const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const OrderedProduct = require("../models/ordered_product").OrderedProduct;
const Like = require("../models/like").Like;
const { ErrorMessage } = require("../constants/error");

/**
 * Gets recommendations of products as users also liked or users also bought.
 *
 * @param {
 *  type: alsoLiked | alsoBought
 *  userId: String
 * } params
 *
 * @returns {[
 *  Product
 * ]}
 */
module.exports.getProducts = async (params) => {
  try {
    if (!params.type || !params.userId) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }

    let productMap = {};
    let products = await Product.find({});

    products.map((product) => (productMap[product._id.toString()] = product.toJSON()));

    /**
     * Stores the product based on user's interaction. For example, if user A liked product X and
     * user B liked product X, Y; stores product Y for user A.
     */
    let usersAlsoProducts = new Set();
    /**
     * Stores relation pairs(like or orderedProduct) as
     *
     * { productId: {userId, userId} }
     */
    const productRelations = {};
    /**
     * Stores relation pairs(like or orderedProduct) as
     *
     * { userId: {productId, productId} }
     */
    const userRelations = {};

    const relations = await (params.type === "alsoLiked" ? Like : OrderedProduct).find({});

    // Create product and user relations
    relations.forEach((relation) => {
      relation = relation.toJSON();

      productRelations[relation.productId.toString()] = productRelations[relation.productId.toString()] || {};
      productRelations[relation.productId.toString()][relation.customerId.toString()] = true;
      userRelations[relation.customerId.toString()] = userRelations[relation.customerId.toString()] || {};
      userRelations[relation.customerId.toString()][relation.productId.toString()] = true;
    });

    // Create usersAlsoProducts
    Object.keys(userRelations[params.userId] || {}).forEach((productId) => {
      Object.keys(productRelations[productId] || {}).forEach((userId) => {
        if (userId !== params.userId) {
          Object.keys(userRelations[userId] || {}).forEach((alsoProductId) => {
            if (alsoProductId !== productId) {
              usersAlsoProducts.add(alsoProductId);
            }
          });
        }
      });
    });

    // Create most products
    const mostProducts = Object.keys(productRelations).sort(
      (a, b) => productRelations[a].length - productRelations[b].length
    );

    // Get recommended product ids
    const productIds = [...usersAlsoProducts].concat(mostProducts.filter((id) => !usersAlsoProducts.has(id)));

    // Map the product ids with products, add unrelated products at the end.
    products = productIds
      .map((id) => productMap[id])
      .concat(Object.values(productMap).filter((product) => !productIds.includes(product._id.toString())))
      .filter((product) => product);

    products = await Promise.all(
      products.map(async (product) => {
        const vendor = await Vendor.findById(product.vendorId);

        return {
          vendor: {
            name: vendor.name,
            rating: vendor.rating,
            id: vendor._id.toString(),
          },
          id: product._id.toString(),
          category: product.category,
          description: product.description,
          name: product.name,
          price: product.price,
          originalPrice: product.originalPrice,
          imageUrl: product.imageUrl,
          rating: product.rating,
          brand: product.brand,
          productInfos: JSON.parse(product.productInfos),
        };
      })
    );

    return { success: true, productList: products };
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
  }
};
