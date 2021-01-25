package com.cmpe352group4.buyo.api

import androidx.lifecycle.LiveData
import com.cmpe352group4.buyo.vo.*
import retrofit2.http.*

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
        @Query("company") company: String,
        @Query("name") name: String
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

    @GET("account")
    fun fetchVendorAccountInformation(
        @Query("id") userId: String,
        @Query("userType") userType: String
    ): LiveData<ApiResponse<BaseResponse<VendorInformationResult>>>

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
    ): LiveData<ApiResponse<BaseResponse<OrderResponse>>>

    @GET("order")
    fun fetchOrdersVendor(
        @Query("id") userId: String,
        @Query("userType") userType: String
    ): LiveData<ApiResponse<BaseResponse<OrderResponseVendor>>>

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

    @POST("account")
    fun saveVendorAccountInfo(
        @Query("id") id: String,
        @Query("userType") userType: String,
        @Query("email") email: String,
        @Query("longitude") longitude: String,
        @Query("latitude") latitude: String,
        @Query("website") website: String,
        @Query("company") company: String
    ): LiveData<ApiResponse<BaseResponsePostRequest>>

    @POST("report/comment")
    fun reportComment(
        @Query("commentId") commentID : String,
        @Query("message") message : String
    ):LiveData<ApiResponse<BaseResponse<ReportCommentResponse>>>

    @POST("report/product")
    fun reportProduct(
        @Query("productId") commentID : String,
        @Query("message") message : String
    ):LiveData<ApiResponse<BaseResponse<ReportProductResponse>>>

    @POST("file")
    fun uploadImage(
        @Body( ) image : ByteArray
    ):LiveData<ApiResponse<BaseResponse<UploadImageResponse>>>

    @GET("vendor/products/{vendorID}")
    fun getVendorProducts(
        @Path("vendorID") vendorID : String
    ) : LiveData<ApiResponse<BaseResponse<VendorProductResponseResult>>>

    @GET("vendor/products")
    fun addProduct(
        @Path("vendorId") vendorID : String,
        @Body() product : List<AddProduct>
    ) : LiveData<ApiResponse<BaseResponse<AddProductResponseResult>>>

    @PATCH("vendor/wholeproducts/{productID}")
    fun updateProduct(
        @Path("productID") productID : String,
        @Body() Product : Product
    ) : LiveData<ApiResponse<BaseResponse<EditProductResponseResult>>>


    @GET("/messages/last")
    fun fetchLastMessages(
        @Query("userType") userType: String,
        @Query("id") id: String
    ) : LiveData<ApiResponse<BaseResponse<LastMessageResponse>>>

    @GET("/messages")
    fun fetchLiveChatMessages(
        @Query("id") id: String,
        @Query("userType") userType: String,
        @Query("withId") withId: String,
        @Query("withType") withType: String
    ) : LiveData<ApiResponse<BaseResponse<LiveChatMessagesResponse>>>

    @POST("/google-signin")
    fun googleSignIn(
        @Query("email") email: String,
        @Query("name") name: String,
        @Query("token") token: String
    ): LiveData<ApiResponse<BaseResponse<LoginSingupResponse>>>

    @POST("/account/forgotPassword")
    fun forgotPassword(
        @Query("email") email: String
    ): LiveData<ApiResponse<BaseResponsePostRequest>>

    @PATCH("order/product")
    fun updateOrderStatus(
        @Query("userId") userId : String,
        @Query("userType") userType : String,
        @Query("status") status : String,
        @Query("orderId") orderId : String,
        @Query("productId") productId : String
    ):LiveData<ApiResponse<BaseResponsePostRequest>>

    @GET("notifications")
    fun fetchNotifications(
        @Query("userType") userType: String,
        @Query("userId") id: String
    ):LiveData<ApiResponse<BaseResponse<NotificationResponse>>>

    @GET("products/recommendation")
    fun fetchRecommendation(
        @Query("userId") userId: String,
        @Query("type") type: String
    ):LiveData<ApiResponse<BaseResponse<ProductResponseRec>>>


}
