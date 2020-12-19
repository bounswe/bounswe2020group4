package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class Cart(
    var products: List<CartProduct>?,
    var totalOriginalPrice: Int,
    var totalPrice: Int?
): Serializable