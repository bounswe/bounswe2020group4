package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class AddCartInfo(
    var attributes: List<Attribute>,
    var quantity: Int
): Serializable