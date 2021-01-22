package com.cmpe352group4.buyo.api

import androidx.lifecycle.LiveData
import com.cmpe352group4.buyo.vo.*
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {

    @GET("product")
    fun fetchProductById(@Query("id") productId:String): LiveData<ApiResponse<BaseResponse
    <ProductResult>>>

    @GET("categories")
    fun fetchCategories(): LiveData<ApiResponse<BaseResponse<CategoryList>>>

    @GET("wishlist")
    fun fetchWishList(
        @Query("customerId") customerId: String
    ): LiveData<ApiResponse<BaseResponse<WishListProducts>>>

    @POST("like")
    fun unlikeProductWishList(
        @Query("customerId") customerId: String,
        @Query("productId") productId: String
    ): LiveData<ApiResponse<BaseResponsePostRequest>>

    @GET("products")
    fun fetchSearchResult(
        @Query("search") searchKeyword: String,
        @QueryMap options: Map<String, String>?
    ) : LiveData<ApiResponse<BaseResponse<ProductResponse>>>

    @GET("products")
    fun fetchProductsbyCategory(
        @Query("categories") categoryList: String,
        @QueryMap options: Map<String, String>?
    ) : LiveData<ApiResponse<BaseResponse<ProductResponse>>>

    @POST("signup")
    fun signup_customer(
        @Query("userType") userType: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): LiveData<ApiResponse<BaseResponse<LoginSingupResponse>>>

    @POST("login")
    fun login_customer(
        @Query("userType") userType: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): LiveData<ApiResponse<BaseResponse<LoginSingupResponse>>>

    @POST("signup")
    fun signup_vendor(
        @Query("userType") userType: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("longitude") longitude: String,
        @Query("latitude") latitude: String,
        @Query("website") website: String,
        @Query("company") company: String
    ): LiveData<ApiResponse<BaseResponse<LoginSingupResponse>>>

    @POST("login")
    fun login_vendor(
        @Query("userType") userType: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): LiveData<ApiResponse<BaseResponse<LoginSingupResponse>>>

    @GET("cart")
    fun fetchCartInfo(
        @Query("customerId") customerId: String
    ): LiveData<ApiResponse<BaseResponse<Cart>>>

    @GET("account")
    fun fetchAccountInformation(
        @Query("id") userId: String,
        @Query("userType") userType: String
    ): LiveData<ApiResponse<BaseResponse<CustomerInformationResult>>>

    @POST("order")
    fun checkout(
        @Query("customerId") customerId: String,
        @Query("creditCard") creditCard: String,
        @Query("address") address: String
        ): LiveData<ApiResponse<BaseResponse<CheckoutResponse>>>

    @POST("cart")
    fun add2cart(
        @Query("customerId") customerId : String,
        @Query("productId") productId : String,
        @Query("productInfo") productInfo : String
    ): LiveData<ApiResponse<BaseResponsePostRequest>>

    @POST("cart")
    fun removeFromCart(
        @Query("customerId") customerId : String,
        @Query("productId") productId : String,
        @Query("productInfo") productInfo : String
    ): LiveData<ApiResponse<BaseResponsePostRequest>>

    @POST("account/address")
    fun addAddress(
        @Query("id") id : String,
        @Query("address") address : String
    ): LiveData<ApiResponse<BaseResponsePostRequest>>

    @POST("account-change-password")
    fun changePassword(
        @Query("id") customerId: String,
        @Query("userType") userType: String,
        @Query("password") password: String
    ): LiveData<ApiResponse<BaseResponsePostRequest>>

    @GET("order")
    fun fetchOrders(
        @Query("id") userId: String,
        @Query("userType") userType: String
    ): LiveData<ApiResponse<BaseResponse<Map<String, Order>>>>

    @POST("account")
    fun saveAccountInfo(
        @Query("id") id: String,
        @Query("userType") userType: String,
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("email") email: String,
        @Query("phoneNumber") phoneNumber: String,
        @Query("gender") gender: String
    ): LiveData<ApiResponse<BaseResponsePostRequest>>

    @POST("report_comment")
    fun reportComment(
        @Query("commentId") commentID : String,
        @Query("message") message : String
    ):LiveData<ApiResponse<BaseResponsePostRequest>>

    @POST("report_product")
    fun reportProduct(
        @Query("productId") commentID : String,
        @Query("message") message : String
    ):LiveData<ApiResponse<BaseResponsePostRequest>>

}
