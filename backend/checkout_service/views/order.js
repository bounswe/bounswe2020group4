const OrderedProduct = require("../models/ordered_product").OrderedProduct;
const CartProduct = require("../models/cart_products").CartProduct;
const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const ObjectId = require("mongoose").Types.ObjectId;
const { checkCreditCard } = require("./verification");
const Moment = require("moment");
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
          await Promise.all(
            cart_product.productInfos.map(async (cartProductInfo) => {
              await Promise.all(
                product.productInfos.map(async (productInfo) => {
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
                      // update the quantity
                      productInfo.stockValue -= cartProductInfo.quantity;
                      // add the ordered product to db
                      await OrderedProduct.create({
                        orderId: randId,
                        address: params.address,
                        customerId: ObjectId(params.customerId),
                        vendorId: product.vendorId,
                        productId: product._id,
                        productInfo: cartProductInfo,
                        date: new Date(),
                      });
                    }
                  }
                })
              );
            })
          );
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
    return error;
  }
};
module.exports.getOrders = async (params) => {
  try {
    let orderedProducts;
    if (params.userType === "customer") {
      orderedProducts = await OrderedProduct.find({ customerId: ObjectId(params.id) });
    } else if (params.userType === "vendor") {
      orderedProducts = await OrderedProduct.find({ vendorId: params.id });
    } else {
      return false;
    }
    if (orderedProducts.length === 0) {
      return false;
    }
    let orders = {};
    orderedProducts = await Promise.all(
      orderedProducts.map(async (orderedProduct) => {
        // create an array for the order, ORDER_ID: [orderedproduct1, orderedproduct2, ...]
        orders[orderedProduct.orderId] = orders[orderedProduct.orderId] || {
          shippingPrice: orderedProduct.shippingPrice,
          address: orderedProduct.address,
          date: orderedProduct.date,
          products: [],
        };
        // get vendor and product information
        const product = await Product.findById(orderedProduct.productId);
        const vendor = await Vendor.findById(orderedProduct.vendorId);
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
          vendor: {
            id: vendor._id,
            name: vendor.name,
            rating: vendor.rating,
          },
          quantity: orderedProduct.productInfo.quantity,
          attributes: orderedProduct.productInfo.attributes,
        };
        // update status according to date
        const orderDate = orderedProduct.date;
        if (Moment(orderDate).add(5, "days").toDate() < new Date()) {
          orderedProduct.status = "Approved";
        } else if (Moment(orderDate).add(3, "days").toDate() < new Date()) {
          orderedProduct.status = "Delivered";
        } else if (Moment(orderDate).add(1, "days").toDate() < new Date()) {
          orderedProduct.status = "Shipped";
        }
        tempOrderedProduct.status = orderedProduct.status;
        orders[orderedProduct.orderId].products.push(tempOrderedProduct);
        await orderedProduct.save();
      })
    );
    return orders;
  } catch (error) {
    console.log(error);
    return error;
  }
};
module.exports.updateOrderStatus = async (params) => {
  try {
	if (!(params.userType && params.status && params.orderId && params.userId)) {
		return false;
	}
	let orderedProducts = ""
	if (params.userType === "customer") {
		orderedProducts = await OrderedProduct.find({ id: ObjectId(params.orderId), customerId: ObjectId(params.userId) });
	} else {
		orderedProducts = await OrderedProduct.find({ id: ObjectId(params.orderId), vendorId: ObjectId(params.userId) });
	}
	orderedProducts = await Promise.all(
		orderedProducts.map(async (product) => {
			product.status = params.status;
			await product.save();
			return product;
		})
	);
	return true;
  } catch (error) {
    console.log(error);
    return error;
  }
};