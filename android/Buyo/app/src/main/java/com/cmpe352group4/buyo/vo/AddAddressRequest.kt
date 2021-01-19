package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class AddAddressRequest(
    var id: String,
    var address: String
): Serializable
