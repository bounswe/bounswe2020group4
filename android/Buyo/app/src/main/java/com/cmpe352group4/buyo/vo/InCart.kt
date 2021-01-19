package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class InCart(
    var products: List<CartProduct>?,
    var discountedPrice: Double,
    var totalPrice: Double
): Serializable