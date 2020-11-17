package com.cmpe352group4.buyo.ui.productList

import java.util.*

data class Product(
    var productImage: String,
    var productName: String,
    var productInfo: String,
    var productID: Int,
    var productReleaseDate: String,
    var productPrice: Double,
    var productNumComments: Int,
    var productRate: Double
)