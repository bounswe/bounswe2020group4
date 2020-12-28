package com.cmpe352group4.buyo.vo

import com.cmpe352group4.buyo.vo.Comment
import com.cmpe352group4.buyo.vo.FilterCriterias
import com.cmpe352group4.buyo.vo.ProductInfo
import com.cmpe352group4.buyo.vo.Vendor
import java.io.Serializable

data class Product(
    var id: String,
    var category: List<String>,
    var name: String,
    var imageUrl: String,
    var rating: Double,
    var price: Double,
    var originalPrice: Double,
    var brand: String,
    var productInfos: List<ProductInfo>,
    var vendor: Vendor,
    var description: String,
    var materials: List<String>?,
    var comments : List<Comment>?,
    var filterCriterias: List<FilterCriterias>?
): Serializable
