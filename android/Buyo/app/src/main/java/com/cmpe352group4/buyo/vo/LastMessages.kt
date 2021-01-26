package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class LastMessages(
    var user: MessageUserInfo,
    var lastMessage: String,
    var date: String
): Serializable