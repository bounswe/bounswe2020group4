package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class LiveChatMessage(
    var id: String,
    var message: String,
    var date: String,
    var user: MessageUserInfo
): Serializable