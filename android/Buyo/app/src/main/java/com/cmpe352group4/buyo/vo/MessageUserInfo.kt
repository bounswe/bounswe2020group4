package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class MessageUserInfo(
    var id: String,
    var name: String,
    var userType: String
): Serializable