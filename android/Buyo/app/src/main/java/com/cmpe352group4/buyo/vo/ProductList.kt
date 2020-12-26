package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class ProductList(
    val productList : List<Product>,
    val filterCriterias: List<String>?
): Serializable