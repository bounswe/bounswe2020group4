package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class Product(
    var productImage: String,
    var productName: String,
    var productInfo: String,
    var productID: Int,
    var productReleaseDate: String,
    var productPrice: String,
    var productNumComments: Int,
    var productRate: Double
): Serializable