package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class SendMessageRequest (
    var userType: String,
    var userId: String,
    var withType: String,
    var withId: String,
    var message: String
): Serializable
