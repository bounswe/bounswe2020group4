package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class DeleteProductEndpoint(
    val vendorID : String,
    val productID : String
):Serializable