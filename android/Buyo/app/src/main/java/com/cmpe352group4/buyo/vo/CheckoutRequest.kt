package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class CheckoutRequest(
    var customerId: String,
    var creditCard: String,
    var address: String
): Serializable
