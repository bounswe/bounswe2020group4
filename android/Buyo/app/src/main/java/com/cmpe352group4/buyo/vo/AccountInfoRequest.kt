package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class AccountInfoRequest (
    var id: String,
    var userType: String,
    var name: String,
    var surname: String,
    var email: String,
    var phoneNumber: String,
    var gender: String
): Serializable