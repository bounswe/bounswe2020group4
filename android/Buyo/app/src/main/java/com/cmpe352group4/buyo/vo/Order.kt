package com.cmpe352group4.buyo.vo

import java.io.Serializable

// data -> MapOf<String, Order>,  string is the order no

data class Order (
    //var orderID : String,
    var shippingPrice: Double,
    var date: String,           //2020-12-28T08:08:37.507Z (order date)
    //var deliverDate: String?, //DD.MM.YYYY
    var products: List<OrderProduct>, // vendor info will come from the product object
    var address: String   // Just the address text
):Serializable

data class OrderProduct (
    var orderedProductId: String,
    var productId: String,
    var name: String,
    var imageUrl: String,
    var rating: Double,
    var price: Double,
    var originalPrice: Double,
    var brand: String,
    var vendor: Vendor,
    var quantity: Int,
    var attributes: List<OrderAttribute>,
    var status: String
): Serializable

data class OrderProductRV (
    var orderID: String,    // For RV, we need these information also
    var address: String,
    var orderDate: String,

    var productId: String,
    var name: String,
    var imageUrl: String,
    var price: Double,
    var vendor: Vendor,
    var quantity: Int,
    var attributes: List<OrderAttribute>,
    var status: String
): Serializable

data class OrderAttribute (
    var name: String,
    var value: String
): Serializable
