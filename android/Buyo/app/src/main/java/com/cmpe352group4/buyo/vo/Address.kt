package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class Address(
    var name: String?,
    var surname: String?,
    var phone: String?,
    var city: String?,
    var addressTitle: String?,
    var address: String?
): Serializable