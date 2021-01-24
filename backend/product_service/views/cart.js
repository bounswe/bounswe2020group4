const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const CartProduct = require("../models/cart_products").CartProduct;
const ObjectId = require("mongoose").Types.ObjectId;
function updateProductInfos(productInfoList, paramProductInfo) {
  let isProductInfoFound = false;
  productInfoList.forEach((dbProductInfo, index) => {
    isCorrectProductInfo = true;
    // Compare product's attributes with the given attributes
    dbProductInfo.attributes.forEach((cart_attr) => {
      paramProductInfo.attributes.forEach((attr) => {
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
    // add all infos except the one that matches(that is going to be removed)
    if (isCorrectProductInfo) {
      productInfoList[index] = paramProductInfo;
      isProductInfoFound = true;
    }
  });
  // If the product info is not updated above, push the new product info to the array.
  if (!isProductInfoFound) {
    productInfoList.push(paramProductInfo);
  }
}
function deleteFromProductInfos(productInfoList, updatedProductInfoList, paramProductInfo) {
  productInfoList.forEach((dbProductInfo) => {
    isCorrectProductInfo = true;
    // Compare product's attributes with the given attributes
    dbProductInfo.attributes.forEach((cart_attr) => {
      paramProductInfo.attributes.forEach((attr) => {
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
    // add all infos except the one that matches(that is going to be removed)
    if (!isCorrectProductInfo) {
      updatedProductInfoList.push(dbProductInfo);
    }
  });
}
module.exports.updateCart = async (params) => {
  try {
    const paramCustomerId = params.customerId;
    const paramProductId = params.productId;
    const paramProductInfo = JSON.parse(params.productInfo);
    const carts = await CartProduct.find({});
    // Delete the product from the cart
    if (!paramProductInfo.quantity) {
      let cart_product = await CartProduct.findOne({
        customerId: ObjectId(paramCustomerId),
        productId: ObjectId(paramProductId),
      });
      cart_product = cart_product.toJSON();
      productInfoList = JSON.parse(cart_product.productInfos);
      let updatedProductInfoList = [];
      // Check each product info in the product
      deleteFromProductInfos(productInfoList, updatedProductInfoList, paramProductInfo);
      // remove completely
      if (!updatedProductInfoList.length) {
        await CartProduct.findByIdAndDelete(cart_product._id);
      } else {
        await CartProduct.findByIdAndUpdate(cart_product._id, { productInfos: JSON.stringify(updatedProductInfoList) });
      }
    } else {
      // Add new product or update the product in the cart
      let cart_product = await CartProduct.findOne({
        customerId: ObjectId(paramCustomerId),
        productId: ObjectId(paramProductId),
      });
      // If the product exists in user's cart before
      if (cart_product) {
        cart_product = cart_product.toJSON();
        productInfoList = JSON.parse(cart_product.productInfos);
        updateProductInfos(productInfoList, paramProductInfo);
        await CartProduct.findByIdAndUpdate(cart_product._id, { productInfos: JSON.stringify(productInfoList) });
      } else {
        // Product doesn't exist in the cart, so it will be created the first time
        let product = await Product.findById(ObjectId(paramProductId));
        product = product.toJSON();
        const vendorId = product.vendorId;

        await CartProduct.create({
          customerId: ObjectId(paramCustomerId),
          vendorId: vendorId,
          productId: ObjectId(paramProductId),
          productInfos: JSON.stringify([paramProductInfo]),
        });

        return true;
      }
    }
    return true;
  } catch (error) {
    console.log(error);
    return error;
  }
};
module.exports.getCartProducts = async (params) => {
  try {
    let cart_products;
    if (params.customerId) {
      cart_products = await CartProduct.find({ customerId: ObjectId(params.customerId) });
    } else {
      return false;
    }
    let productsWithAttributes = [];
    let totalPrice = 0;
    let discountedPrice = 0;
    await Promise.all(
      cart_products.map(async (cart_product) => {
        cart_product = cart_product.toJSON();
        const product = await Product.findById(cart_product.productId);
        const vendor = await Vendor.findById(cart_product.vendorId);
        console.log(
          typeof cart_product.productInfos,
          typeof JSON.parse(cart_product.productInfos),
          cart_product.productInfos
        );
        JSON.parse(cart_product.productInfos).forEach((productInfo) => {
          let updatedProduct = {
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
            quantity: productInfo.quantity,
          };
          totalPrice += product.originalPrice * productInfo.quantity;
          discountedPrice += product.price * productInfo.quantity;
          productsWithAttributes.push(updatedProduct);
        });
      })
    );
    return { products: productsWithAttributes, totalPrice, discountedPrice };
  } catch (error) {
    console.log(error);
    return error;
  }
};
module.exports.emptyCart = async (params) => {
  try {
    if (params.id) {
      await CartProduct.deleteMany({ customerId: ObjectId(params.id) });
      return true;
    } else {
      return false;
    }
  } catch (error) {
    return error;
  }
};
