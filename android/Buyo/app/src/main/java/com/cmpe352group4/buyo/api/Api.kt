package com.cmpe352group4.buyo.api

import androidx.lifecycle.LiveData
import com.cmpe352group4.buyo.vo.*
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("product")
    fun fetchProductById(@Query("id") productId:Int): LiveData<ApiResponse<BaseResponse
    <ProductResult>>>

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

    @GET("products")
    fun fetchSearchResult(@Query("search") searchKeyword: String) : LiveData<ApiResponse<BaseResponse<ProductList>>>

    @GET("products")
    fun fetchProductsbyCategory(@Query("categories") categoryList: String) : LiveData<ApiResponse<BaseResponse<ProductList>>>

    @POST("signup")
    fun signup(
        @Query("userType") userType: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): LiveData<ApiResponse<BaseResponse<LoginSingupResponse>>>

    @POST("login")
    fun login(
        @Query("userType") userType: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): LiveData<ApiResponse<BaseResponse<LoginSingupResponse>>>

}