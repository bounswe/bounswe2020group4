package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class AddProductRequest(
    val vendorID : String,
    val products : List<AddProduct>
):Serializable