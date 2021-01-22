package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class VendorAccountInfoRequest (
    var id: String,
    var userType: String,
    var email: String,
    var longitude: String,
    var latitude: String,
    var website: String,
    var company: String
): Serializable