package com.cmpe352group4.buyo.vo

import android.os.Parcelable
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
    var materials: List<String>?
): Serializable
