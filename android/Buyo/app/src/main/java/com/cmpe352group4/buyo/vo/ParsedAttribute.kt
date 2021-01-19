package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class ParsedAttribute (
    var att_name : String,
    var att_value : List<String>
) : Serializable
