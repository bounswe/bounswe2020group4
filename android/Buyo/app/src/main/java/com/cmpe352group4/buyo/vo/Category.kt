package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class Category(
    var name: String,
    var path: String,
    var subcategories: List<Category>?
): Serializable