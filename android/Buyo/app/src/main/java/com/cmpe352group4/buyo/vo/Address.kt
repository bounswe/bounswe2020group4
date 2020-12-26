package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class Address(
    var id: String?,
    var name: String?,
    var surname: String?,
    var phone: String?,
    var province: String?,
    var city: String?,
    var street: String,
    var addressTitle: String?,
    var address: String?
): Serializable