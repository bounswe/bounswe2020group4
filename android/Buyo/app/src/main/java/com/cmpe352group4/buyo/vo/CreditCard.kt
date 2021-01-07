package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class CreditCard(
    var name: String,
    var number: String,
    var expirationMonth: String,
    var expirationYear: String,
    var cvc: String?
): Serializable

