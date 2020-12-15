package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class Comment (
    var user : String,
    var text : String,
    var rating : String
):Serializable