package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class CartProduct(
    var category: List<String>,
    var size: String?,
    var color: String?,
    var name: String,
    var id: Int,
    var imageUrl: String,
    var rating: Double,
    var price: Double,
    var originalPrice: Double,
    var brand: String,
    var stockValue: Map<String, Int>?,
    var vendor: Vendor,
    var quantity: Int?
): Serializable
