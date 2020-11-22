package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class LoginSignupRequest(
    var userType: String,
    var email: String,
    var password: String
): Serializable