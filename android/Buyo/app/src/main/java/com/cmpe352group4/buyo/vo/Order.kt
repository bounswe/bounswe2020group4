package com.cmpe352group4.buyo.vo

import java.io.Serializable
import java.util.*

data class Order (
    var orderID : String,
    var orderDate: String?,   //YYYY-MM-DD
    var deliverDate: String?, //YYYY-MM-DD
    var isDelivered: Boolean,
    var product: Product, // vendor info will come from the product object
    var address: String   // Just the address text
    ):Serializable
