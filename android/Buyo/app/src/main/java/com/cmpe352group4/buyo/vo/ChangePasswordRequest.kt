package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class ChangePasswordRequest (
    var id: String,
    var userType: String,
    var password: String
): Serializable