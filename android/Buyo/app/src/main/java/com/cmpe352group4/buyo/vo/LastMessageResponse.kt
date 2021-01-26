package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class LastMessageResponse(
    var lastMessages: List<LastMessages>?
): Serializable