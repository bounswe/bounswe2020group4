package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class LikeResponse(
    var custID: Int,
    var prodID: String
): Serializable