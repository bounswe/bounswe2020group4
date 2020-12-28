package com.cmpe352group4.buyo.api

import androidx.lifecycle.LiveData
import com.cmpe352group4.buyo.vo.*
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
    fun fetchSearchResult(@Query("search") searchKeyword: String) : LiveData<ApiResponse<BaseResponse<ProductResponse>>>

    @GET("products")
    fun fetchProductsbyCategory(@Query("categories") categoryList: String) : LiveData<ApiResponse<BaseResponse<ProductResponse>>>

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
        @Query("creditCard") creditCard: CreditCard,
        @Query("address") address: Address
        ): LiveData<ApiResponse<BaseResponse<CheckoutResponse>>>

    @GET("order")
    fun fetchOrders(
        @Query("id") userId: String,
        @Query("userType") userType: String
    ): LiveData<ApiResponse<BaseResponse<Map<String, Order>>>>

}
