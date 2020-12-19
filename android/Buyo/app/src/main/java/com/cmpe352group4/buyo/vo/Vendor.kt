package com.cmpe352group4.buyo.vo

import java.io.Serializable


data class Vendor(
    var id : Int?,
    var name : String,
    var rating : Double
): Serializable
