package com.cmpe352group4.buyo.api

import androidx.lifecycle.LiveData
import com.cmpe352group4.buyo.vo.BaseResponse
import com.cmpe352group4.buyo.vo.Category
import com.cmpe352group4.buyo.vo.CategoryList
import com.cmpe352group4.buyo.vo.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    // These are some example to define endpoints

//    @GET("products")
//    fun fetchDailyProgram(): LiveData<ApiResponse<ProductList>>
//
//    @GET("product")
//    fun getProductDetail(
//        @Query("id") productId: Int
//    ): LiveData<ApiResponse<Product>>
//
    @GET("product/{id}")
    fun fetchProductDetail(@Path("id") productId:Int): LiveData<ApiResponse<Product>>

    @GET("categories")
    fun fetchCategories(): LiveData<ApiResponse<BaseResponse<CategoryList>>>




}