package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class Order (
    var orderID : String,
    var orderDate: String?,   //DD.MM.YYYY
    var deliverDate: String?, //DD.MM.YYYY
    var isDelivered: Boolean,
    var product: Product, // vendor info will come from the product object
    var address: String   // Just the address text
    ):Serializable
