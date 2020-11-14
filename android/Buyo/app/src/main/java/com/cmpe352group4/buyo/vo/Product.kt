package com.cmpe352group4.buyo.vo

import java.io.Serializable

data class Product (
    val id:Int,
    val photo:String,
    val description:String,
    val price:Int

): Serializable