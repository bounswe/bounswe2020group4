package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class LiveChatMessagesRequest(
    var id: String,
    var userType: String,
    var withId: String,
    var withType: String
): Serializable