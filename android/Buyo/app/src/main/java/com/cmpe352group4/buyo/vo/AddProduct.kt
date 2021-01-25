package com.cmpe352group4.buyo.vo

import java.io.Serializable

class AddProduct (
    var id: String,
    var category: List<String>,
    var name: String,
    var imageUrl: String,
    var rating: Double,
    var price: Double,
    var originalPrice: Double,
    var brand: String,
    var productInfos: MutableList<ProductInfo>,
    var vendorId: String,
    var description: String,
    var materials: List<String>?,
    var comments : List<Comment>?,
    var filterCriterias: List<FilterCriterias>?
): Serializable