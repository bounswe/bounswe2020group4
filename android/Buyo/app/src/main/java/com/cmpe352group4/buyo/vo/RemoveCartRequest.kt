package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class RemoveCartRequest(
    var customerId: String,
    var productId: String,
    var productInfo: String
): Serializable