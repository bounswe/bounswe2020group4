const OrderedProduct = require("../models/ordered_product").OrderedProduct;
const CartProduct = require("../models/cart_products").CartProduct;
const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const ObjectId = require("mongoose").Types.ObjectId;
const { checkCreditCard } = require("./verification");
const Moment = require("moment");
const { ErrorMessage } = require("../constants/error");
// TODO(eridincu): UNAVAILABLE PRODUCTS BEHAVIOUR
module.exports.checkoutOrder = async (params) => {
  try {
    if (!checkCreditCard(JSON.parse(params.creditCard))) {
      return { success: false, msg: "Credit card information is invalid." };
    }
    if (params.customerId && params.address) {
      cart_products = await CartProduct.find({ customerId: ObjectId(params.customerId) });
      // simple random id generator
      const randId = (Date.now().toString(36) + Math.random().toString(36).substr(2, 5)).toUpperCase();
      let unavailableProducts = [];
      let orderedProducts = [];
      await Promise.all(
        cart_products.map(async (cart_product) => {
          cart_product = cart_product.toJSON();
          let product = await Product.findById(cart_product.productId);
          const vendor = (await Vendor.findById(cart_product.vendorId)).toJSON();
          let productTempInfos = JSON.parse(product.productInfos);
          await Promise.all(
            JSON.parse(cart_product.productInfos).map(async (cartProductInfo) => {
              await Promise.all(
                productTempInfos.map(async (productInfo) => {
                  let isCorrectProductInfo = true;
                  cartProductInfo.attributes.forEach((cart_attr) => {
                    productInfo.attributes.forEach((attr) => {
                      if (attr.name === cart_attr.name) {
                        if (attr.value !== cart_attr.value) {
                          isCorrectProductInfo = false;
                          return;
                        }
                      }
                    });
                    if (!isCorrectProductInfo) {
                      return;
                    }
                  });
                  if (isCorrectProductInfo) {
                    // if product is not available in stock
                    if (productInfo.stockValue < cartProductInfo.quantity) {
                      unavailableProducts.push({
                        id: product._id,
                        name: product.name,
                        imageUrl: product.imageUrl,
                        rating: product.rating,
                        price: product.price,
                        originalPrice: product.originalPrice,
                        brand: product.brand,
                        vendor: {
                          id: product.vendorId,
                          name: vendor.name,
                          rating: vendor.rating,
                        },
                        attributes: productInfo.attributes,
                        availableStock: productInfo.stockValue,
                      });
                    } else {
                      // it is available in stock, so it will be included as ordered.
                      orderedProducts.push({
                        id: product._id,
                        name: product.name,
                        imageUrl: product.imageUrl,
                        rating: product.rating,
                        price: product.price,
                        originalPrice: product.originalPrice,
                        brand: product.brand,
                        vendor: {
                          id: product.vendorId,
                          name: vendor.name,
                          rating: vendor.rating,
                        },
                        attributes: cartProductInfo.attributes,
                        quantity: cartProductInfo.quantity,
                      });
                      // update the stock value accordingly
                      productInfo.stockValue -= cartProductInfo.quantity;
                      // add the ordered product to db
                      await OrderedProduct.create({
                        orderId: randId,
                        address: params.address,
                        customerId: ObjectId(params.customerId),
                        vendorId: product.vendorId,
                        productId: product._id,
                        productInfo: JSON.stringify(cartProductInfo),
                        date: new Date(),
                      });
                    }
                  }
                })
              );
            })
          );
          product.productInfos = JSON.stringify(productTempInfos);
          await product.save();
        })
      );
      await CartProduct.deleteMany({ customerId: ObjectId(params.customerId) });
      const cartInfo = {
        success: true,
        data: {
          cartId: randId,
          orderedProducts: orderedProducts,
          unavailableProducts: unavailableProducts,
          customerId: params.customerId,
        },
      };
      return cartInfo;
    } else {
      return { success: false, msg: "Missing arguments." };
    }
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
  }
};
module.exports.getOrders = async (params) => {
  try {
    let orderedProducts;
    if (params.userType === "customer") {
      orderedProducts = await OrderedProduct.find({ customerId: ObjectId(params.id) });
    } else if (params.userType === "vendor") {
      orderedProducts = await OrderedProduct.find({ vendorId: ObjectId(params.id) });
    } else {
      return { success: false, message: ErrorMessage.WRONG_USER_TYPE };
    }
    if (orderedProducts.length === 0) {
      return { success: false, message: ErrorMessage.PRODUCT_NOT_FOUND };
    }
    let orders = {};
    let sum = 0;
    orderedProducts = await Promise.all(
      orderedProducts.map(async (orderedProduct) => {
        // create an array for the order, ORDER_ID: [orderedproduct1, orderedproduct2, ...]
        orders[orderedProduct.orderId] = orders[orderedProduct.orderId] || {
          shippingPrice: orderedProduct.shippingPrice,
          address: orderedProduct.address,
          date: orderedProduct.date,
          products: [],
        };
        let parsedProductInfo = JSON.parse(orderedProduct.productInfo);
        const product = await Product.findById(orderedProduct.productId);

        // the product json which returned to client
        let tempOrderedProduct = {
          orderedProductId: orderedProduct._id,
          productId: product._id,
          name: product.name,
          imageUrl: product.imageUrl,
          rating: product.rating,
          price: product.price,
          originalPrice: product.originalPrice,
          brand: product.brand,
          quantity: parsedProductInfo.quantity,
          attributes: parsedProductInfo.attributes,
        };
        // get vendor and product information
        if (params.userType === "customer") {
          const vendor = await Vendor.findById(orderedProduct.vendorId);
          // the product json which returned to client
          tempOrderedProduct.vendor = {
            id: vendor._id,
            name: vendor.name,
            rating: vendor.rating,
          };
        } else {
          tempOrderedProduct.customerId = orderedProduct.customerId.toString();

          if (orderedProduct.status !== "Cancelled" && orderedProduct.status !== "Returned") {
            sum += tempOrderedProduct.price * tempOrderedProduct.quantity;
          }
        }
        // update status according to date.
        const orderDate = orderedProduct.date;
        orderedProduct = updateStatusByDate(orderedProduct, orderDate);
        // after updating the status, add the product to the list returned to the client.
        tempOrderedProduct.status = orderedProduct.status;
        orders[orderedProduct.orderId].products.push(tempOrderedProduct);
        await orderedProduct.save();
      })
    );
    return { success: true, data: { orders: orders, totalEarnings: sum } };
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
  }
};
module.exports.updateProductStatus = async (params) => {
  try {
    if (!(params.userType && params.status && params.userId && params.productId && params.orderId)) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }
    let orderedProduct;
    if (params.userType == "customer") {
      orderedProduct = await OrderedProduct.findOne({
        orderId: params.orderId,
        customerId: ObjectId(params.userId),
        productId: ObjectId(params.productId),
      });
    } else if (params.userType == "vendor") {
      orderedProduct = await OrderedProduct.findOne({
        orderId: params.orderId,
        vendorId: ObjectId(params.userId),
        productId: ObjectId(params.productId),
      });
    } else {
      return { success: false, message: ErrorMessage.WRONG_USER_TYPE };
    }
    // Check if product is found.
    if (!orderedProduct) {
      return { success: false, message: ErrorMessage.PRODUCT_NOT_FOUND };
    }
    // Check if vendor is trying to return a product.
    if (params.status === "Returned" && params.userType === "vendor") {
      return { success: false, message: ErrorMessage.VENDOR_CANNOT_RETURN };
    }
    // Product is already cancelled or returned.
    if (orderedProduct.status == "Cancelled" || orderedProduct.status == "Returned") {
      return { success: false, message: ErrorMessage.ALREADY_CANCEL_OR_RETURN };
    }
    // Check if cancelling that product is valid.
    if (params.status == "Cancelled") {
      if (Moment(orderedProduct.date).add(1, "days").toDate() < new Date()) {
        return { success: false, message: ErrorMessage.ALREADY_SHIPPED };
      } else {
        // TODO(eridincu): Update stock value of the product in the product database.
        let db_product = await Product.findById(orderedProduct.productId);
        if (!db_product) {
          return { success: false, message: ErrorMessage.PRODUCT_NOT_FOUND };
        }
        let dbTempProductInfos = JSON.parse(db_product.productInfos);
        // update the stock value of the product in product DB
        await Promise.all(
          dbTempProductInfos.map(async (productInfo) => {
            // find the correct productInfo of the product in the product database
            let isCorrectProductInfo = true;
            productInfo.attributes.forEach((attr) => {
              JSON.parse(orderedProduct.productInfo).attributes.forEach((order_attr) => {
                if (attr.name === order_attr.name) {
                  if (attr.value !== order_attr.value) {
                    isCorrectProductInfo = false;
                    return;
                  }
                }
              });
              if (!isCorrectProductInfo) {
                return;
              }
            });
            // update the stock value of the product in product DB
            if (isCorrectProductInfo) {
              productInfo.stockValue += JSON.parse(orderedProduct.productInfo).quantity;
            }
          })
        );
        // save updated stock values
        db_product.productInfos = JSON.stringify(dbTempProductInfos);
        await db_product.save();

        orderedProduct.status = params.status;

        await orderedProduct.save();
      }
    } else if (params.status == "Returned") {
      // Check if the product can be returned.
      if (!(Moment(orderedProduct.date).add(3, "days").toDate() < new Date())) {
        return { success: false, message: ErrorMessage.SHOULD_BE_DELIVERED };
      } else {
        let db_product = await Product.findById(orderedProduct.productId);
        if (!db_product) {
          return { success: false, message: ErrorMessage.PRODUCT_NOT_FOUND };
        }
        let dbTempProductInfos = JSON.parse(db_product.productInfos);
        // update the stock value of the product in product DB
        await Promise.all(
          dbTempProductInfos.map(async (productInfo) => {
            // find the correct productInfo of the product in the product database
            let isCorrectProductInfo = true;
            productInfo.attributes.forEach((attr) => {
              JSON.parse(orderedProduct.productInfo).attributes.forEach((order_attr) => {
                if (attr.name === order_attr.name) {
                  if (attr.value !== order_attr.value) {
                    isCorrectProductInfo = false;

                    return;
                  }
                }
              });
              if (!isCorrectProductInfo) {
                return;
              }
            });
            // update the stock value of the product in product DB
            if (isCorrectProductInfo) {
              productInfo.stockValue += orderedProduct.productInfo.quantity;
            }
          })
        );
        db_product.productInfos = JSON.stringify(dbTempProductInfos);
        await db_product.save();

        orderedProduct.status = params.status;
        await orderedProduct.save();
      }
    } else {
      orderedProduct.status = params.status;
      await orderedProduct.save();
    }
    return { success: true };
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
  }
};
module.exports.updateOrderStatus = async (params) => {
  try {
    if (!(params.userType && params.status && params.orderId && params.userId)) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }
    let orderedProducts;
    if (params.userType === "customer") {
      console.log(params, typeof params.userId);
      console.log(await OrderedProduct.find());

      orderedProducts = await OrderedProduct.find({
        orderId: params.orderId,
        customerId: ObjectId(params.userId),
      });
    } else {
      orderedProducts = await OrderedProduct.find({
        orderId: params.orderId,
        vendorId: ObjectId(params.userId),
      });
    }
    if (orderedProducts.length === 0) {
      console.log("not found");
      return { success: false, message: ErrorMessage.PRODUCT_NOT_FOUND };
    }
    if (params.status === "Cancelled") {
      if (Moment(params.date).add(1, "days").toDate() < new Date()) {
        return { success: false, message: ErrorMessage.ALREADY_SHIPPED };
      }
    } else if (params.status === "Returned") {
      if (!(Moment(product.date).add(3, "days").toDate() < new Date())) {
        return { success: false, message: ErrorMessage.SHOULD_BE_DELIVERED };
      }
    }
    orderedProducts = await Promise.all(
      orderedProducts.map(async (orderedProduct) => {
        let db_product = await Product.findById(orderedProduct.productId);
        if (!db_product) {
          return { success: false, message: ErrorMessage.PRODUCT_NOT_FOUND };
        }
        // Stock value of cancelled or returned products should be updated.
        if (params.status === "Returned" || params.status === "Cancelled") {
          let dbTempProductInfos = JSON.parse(db_product.productInfos);
          // update the stock value of the product in product DB
          await Promise.all(
            dbTempProductInfos.map(async (productInfo) => {
              // find the correct productInfo of the product in the product database
              let isCorrectProductInfo = true;
              productInfo.attributes.forEach((attr) => {
                JSON.parse(orderedProduct.productInfo).attributes.forEach((order_attr) => {
                  if (attr.name === order_attr.name) {
                    if (attr.value !== order_attr.value) {
                      isCorrectProductInfo = false;
                      return;
                    }
                  }
                });
                if (!isCorrectProductInfo) {
                  return;
                }
              });
              // update the stock value of the product in product DB
              if (isCorrectProductInfo) {
                productInfo.stockValue += JSON.parse(orderedProduct.productInfo).quantity;
              }
            })
          );
          db_product.productInfos = JSON.stringify(dbTempProductInfos);
          await db_product.save();
        }
        orderedProduct.status = params.status;
        await orderedProduct.save();
        return orderedProduct;
      })
    );
    return { success: true };
  } catch (error) {
    console.log(error);
    return { success: false, message: error.message || error };
  }
};
// Check if the product is cancelled before; if not, update according to the date.
function updateStatusByDate(product, date) {
  try {
    if (product.status == "Cancelled" || product.status == "Returned") {
      // DO NOTHING
    } else if (Moment(date).add(5, "days").toDate() < new Date()) {
      product.status = "Approved";
    } else if (Moment(date).add(3, "days").toDate() < new Date()) {
      product.status = "Delivered at " + Moment(date).add(3, "days").toDate().toString();
    } else if (Moment(date).add(1, "days").toDate() < new Date()) {
      product.status = "Shipped";
    }
    return product;
  } catch (error) {
    console.log(error);
  }
}
