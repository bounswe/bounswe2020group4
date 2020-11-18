package com.cmpe352group4.buyo.ui.homepage

import java.util.*

// TODO Merge this file with emre's branch

data class Product(
    var productImage: String,
    var productName: String,
    var productInfo: String,
    var productID: Int,
    var productReleaseDate: String,
    var productPrice: Double,  // TODO Price should be string , i think
    var productNumComments: Int,
    var productRate: Double
)