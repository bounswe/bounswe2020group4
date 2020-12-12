const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const CartProduct = require("../models/cart_products").CartProduct;
const Counter = require("../models/counter").Counter;

module.exports.updateCart = async (params) => {
    try {
        const isInCart = await CartProduct.countDocuments({ customerId: params.customerId, productId: params.productId });

        if (isInCart) {
            await CartProduct.deleteOne({ customerId: params.customerId, productId: params.productId });
        } else {
            const userCart = await CartProduct.findOne({ customerId: params.customerId });
            if (userCart) {
                await CartProduct.create({cartId: userCart.cartId, customerId: params.customerId, vendorId: params.vendorId, productId: params.productId, status: userCart.status });
            } else {
                const counter = await Counter.findOne();
                counter["cartCounter"]++;
                await counter.save();

                await CartProduct.create({
                    cartId: counter["cartCounter"], 
                    customerId: params.customerId, 
                    vendorId: params.vendorId, 
                    productId: params.productId, 
                    status: params.status, 
                });
            }
        }
        return true;
    } catch (error) {
        return error;
    }
};

module.exports.getCartProducts = async (params) => {
    try {
        let products;

        if (params.customerId) {
            products = await Product.find({ customerId: { $all: JSON.parse(params.customerId) } });
        } else {
            return false;
        }

        products = await Promise.all(
            products.map(async (product => {
                product = product.toJSON();

                const vendor = await Vendor.findOne({ id: product.vendorId });

                product.vendor = {
                    name: vendor.name,
                    rating: vendor.rating,
                    id: vendor.id,
                };

                delete product._id;
                delete product.vendorId;

                return product;
            }))
        );

        return products;
    } catch (error) {
        return error;
    }
};