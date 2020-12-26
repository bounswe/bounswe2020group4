package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class CreditCard(
    var name: String,
    var number: Int,
    var expirationMonth: Int,
    var expirationYear: Int,
    var cvc: Int?
): Serializable

