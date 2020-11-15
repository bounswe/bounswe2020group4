package com.cmpe352group4.buyo.ui.productList

import java.text.DateFormat

data class Product(
    var productImage: String,
    var productName: String,
    var productInfo: String,
    var productID: Int,
    var productReleaseDate: DateFormat,
    var productPrice: Float,
    var productNumComments: Int,
    var productRate: Float
)