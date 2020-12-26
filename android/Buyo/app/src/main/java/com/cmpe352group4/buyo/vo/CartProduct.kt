package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class CartProduct(
    var id: String,
    var name: String,
    var imageUrl: String,
    var rating: Double,
    var price: Double,
    var originalPrice: Double,
    var brand: String,
    var productInfo: List<ProductInfo>,
    var vendor: Vendor
): Serializable

