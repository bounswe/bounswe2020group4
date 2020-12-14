const Order = require("../models/order").Order;
const CartProduct = require("../models/cart_products").CartProduct;
const ObjectId = require("mongoose").Types.ObjectId;

module.exports.addOrder = async (params) => {
    try {
        if (params.customerId && params.address && params.paymentType) {
            products = await CartProduct.find({ customerId: ObjectId(params.customerId) });
            
            // simple random id generator
            const randId = (Date.now().toString(36) + Math.random().toString(36).substr(2, 5)).toUpperCase();

            products = await Promise.all(
                products.map(async (cart_product => {
                    cart_product = cart_product.toJSON();

                    const result = await Order.create({
                       id: randId, 
                       address: params.address,
                       customerId: cart_product.customerId,
                       vendorId: cart_product.vendorId,
                       productId: cart_product.productId,
                       paymentType: params.paymentType,
                    });
                }))
            );
            await CartProduct.deleteMany({ customerId: ObjectId(params.customerId) });

            return randId; 
        } else {
            return false;
        }
    } catch (error) {
        return error;
    }
};

module.exports.getOrders = async (params) => {
    try {
        let orderedProducts;
        
        if (params.userType === "customer") {
            orderedProducts = await Order.find({ customerId: ObjectId(params.id) });
        } else if (params.userType === "vendor") {
            orderedProducts = await Order.find({ vendorId: params.id });
        } else {
            return false;
        }

        if (orderedProducts.length === 0) {
            return false;
        }

        let orders = {};
        
        orderedProducts = await Promise.all(
            orderedProducts.map(async (product) => {
                product = product.toJSON();
                
                orders[product.id] = orders[product.id] || [];
                orders[product.id].push({
                    productId: product.productId.toString(),
                    status: product.status,
                    paymentType: product.paymentType,
                    address: product.address,
                    date: product.date,
                    customerId: product.customerId,
                    vendorId: product.vendorId,
                });
            })
        );

        return orders;
    } catch (error) {
        return error;
    }
};

module.exports.updateOrderStatus = async (params) => {
    try {
        if (params.status && params.id) {
            let orderedProducts = await Order.find({ id: ObjectId(params.id) });
            
            orderedProducts = await Promise.all(
                orderedProducts.map(async (product => {
                    product.status = params.status;

                    await product.save();

                    return product;
                }))
            );

            return true;
        } else {
            return false;
        }
    } catch (error) {
        return error;
    }
};