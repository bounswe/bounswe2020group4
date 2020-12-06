package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class Product(
    var category: List<String>,
    var sizes: String?,
    var colors: List<String>?,
    var name: String,
    var id: Int,
    var imageUrl: String,
    var rating: Double,
    var price: Double,
    var originalPrice: Double,
    var brand: String,
    var stockValue: Map<String, Int>,
    var vendor: Vendor
): Serializable
