package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class LiveChatMessagesResponse(
    var messages: List<LiveChatMessage>?
): Serializable