package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class FilterCriterias (
    val name : String,
    val displayName : String,
    val possibleValues : List<String>
):Serializable