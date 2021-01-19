package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class CheckoutResponse(
    var cardId: String,
    var orderedProducts: List<CartProduct>?,
    var unavailableProducts: List<CartProduct>?,
    var customerId: String
): Serializable