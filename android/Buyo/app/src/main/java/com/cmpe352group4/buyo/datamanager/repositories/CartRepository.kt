package com.cmpe352group4.buyo.datamanager.repositories

import androidx.lifecycle.LiveData
import com.cmpe352group4.buyo.api.Api
import com.cmpe352group4.buyo.api.ApiResponse
import com.cmpe352group4.buyo.api.NetworkServiceWrapper
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.base.AppExecutors
import com.cmpe352group4.buyo.base.ConnectionManager
import com.cmpe352group4.buyo.util.livedata.InitialLiveData
import com.cmpe352group4.buyo.vo.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val api: Api,
    private val connectionManager: ConnectionManager
){

    fun getCartInfo(userId:String): LiveData<Resource<Cart>> {
        return object : NetworkServiceWrapper<Cart, BaseResponse<Cart>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<Cart>): LiveData<Cart> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<Cart>>> = api.fetchCartInfo(userId)
        }.asLiveData()
    }

    fun checkout(checkoutRequest: CheckoutRequest): LiveData<Resource<CheckoutResponse>> {
        return object : NetworkServiceWrapper<CheckoutResponse, BaseResponse<CheckoutResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<CheckoutResponse>): LiveData<CheckoutResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<CheckoutResponse>>> = api.checkout(checkoutRequest.customerId, checkoutRequest.creditCard, checkoutRequest.address)
        }.asLiveData()
    }

    fun add2cart(customerId: String, productId: String, productInfo: String): LiveData<Resource<BaseResponsePostRequest>> {
        return object : NetworkServiceWrapper<BaseResponsePostRequest, BaseResponsePostRequest>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponsePostRequest): LiveData<BaseResponsePostRequest> {
                return InitialLiveData.create(data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponsePostRequest>> = api.add2cart(customerId, productId, productInfo)
        }.asLiveData()
    }

    fun removeFromCart(customerId: String, productId: String, productInfo: String): LiveData<Resource<BaseResponsePostRequest>> {
        return object : NetworkServiceWrapper<BaseResponsePostRequest, BaseResponsePostRequest>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponsePostRequest): LiveData<BaseResponsePostRequest> {
                return InitialLiveData.create(data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponsePostRequest>> = api.removeFromCart(customerId, productId, productInfo)
        }.asLiveData()
    }


}