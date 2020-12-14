const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const CartProduct = require("../models/cart_products").CartProduct;
const ObjectId = require("mongoose").Types.ObjectId;

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
                await CartProduct.create({
                    customerId: ObjectId(params.customerId), 
                    vendorId: ObjectId(params.vendorId), 
                    productId: ObjectId(params.productId), 
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
            products = await Product.find({ customerId: ObjectId(params.customerId) });
        } else {
            return false;
        }

        products = await Promise.all(
            products.map(async (product) => {
                product = product.toJSON();

                const vendor = await Vendor.findOne({ _id: product.vendorId });

                product.vendor = {
                    name: vendor.name,
                    rating: vendor.rating,
                    id: vendor._id,
                };

                delete product._id;
                delete product.vendorId;

                return product;
            })
        );

        return products;
    } catch (error) {
        return error;
    }
};

module.exports.emptyCart = async (params) => {
    try {
        if (params.id) {
            await CartProduct.deleteMany({ customerId: ObjectId(params.customerId) });
            return true;
        } else {
            return false;
        }
    } catch (error) {
        return error;
    }
};