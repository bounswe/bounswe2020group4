package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class SearchKeywordRequest(
    var keyword : String,
    var filterSort : Map<String, String>?
):Serializable