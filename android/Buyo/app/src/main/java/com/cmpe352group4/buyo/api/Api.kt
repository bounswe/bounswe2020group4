package com.cmpe352group4.buyo.api

import androidx.lifecycle.LiveData
import com.cmpe352group4.buyo.vo.*
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
    @GET("product")
    fun fetchProductById(@Query("id") productId:Int): LiveData<ApiResponse<BaseResponse
                                                                                <ProductResult>>>

    @GET("categories")
    fun fetchCategories(): LiveData<ApiResponse<BaseResponse<CategoryList>>>

    @GET("products")
    fun fetchSearchResult(@Query("search") searchKeyword: String) : LiveData<ApiResponse<BaseResponse<ProductList>>>

    @GET("products")
    fun fetchProductsbyCategory(@Query("categories") categoryList: String) : LiveData<ApiResponse<BaseResponse<ProductList>>>


}