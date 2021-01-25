package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class SendMessageSocket (
    var userType: String,
    var id: String,
    var withType: String,
    var withId: String,
    var message: String
): Serializable
