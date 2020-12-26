package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class Product(
    var category: List<String>,
    var sizes: List<String>,
    var colors: List<String>?,
    var name: String,
    var id: String,
    var imageUrl: String,
    var rating: Double,
    var price: Double,
    var originalPrice: Double,
    var brand: String,
    var productInfos: List<ProductInfo>,
    var vendor: Vendor,
    var description: String,
    var stockValue: Map<String, Int>
): Serializable
