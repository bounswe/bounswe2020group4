package com.cmpe352group4.buyo.vo

import java.io.Serializable


data class SearchCategoryRequest (
    var categories : String,
    var filterSort : Map<String, String>?
):Serializable