package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class VendorInformation (
    var email: String?,
    var password: String?,
    var longitude: String?,
    var latitude: String?,
    var website: String?,
    var company: String?
): Serializable