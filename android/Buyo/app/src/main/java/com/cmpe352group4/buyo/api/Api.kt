package com.cmpe352group4.buyo.api

import androidx.lifecycle.LiveData
import com.cmpe352group4.buyo.vo.*
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("wishlist")
    fun fetchWishList(
        @Query("customerId") customerId: Int
    ): LiveData<ApiResponse<BaseResponse<WishListProducts>>>


    @POST("like")
    fun unlikeProductWishList(
        @Query("customerId") customerId: Int,
        @Query("productId") productId: Int
    ): LiveData<ApiResponse<BaseResponsePostRequest>>
}