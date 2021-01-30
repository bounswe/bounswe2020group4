package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class LastMessageRequest(
    var id: String,
    var userType: String
): Serializable