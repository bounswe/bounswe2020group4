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
class OrderRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val api: Api,
    private val connectionManager: ConnectionManager
){

    fun getOrders(userId:String): LiveData<Resource<OrderResponse>> {
        return object : NetworkServiceWrapper<OrderResponse, BaseResponse<OrderResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<OrderResponse>): LiveData<OrderResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<OrderResponse>>> = api.fetchOrders(userId, "customer")
        }.asLiveData()
    }

    fun getOrdersVendor(userId:String): LiveData<Resource<OrderResponseVendor>> {
        return object : NetworkServiceWrapper<OrderResponseVendor, BaseResponse<OrderResponseVendor>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<OrderResponseVendor>): LiveData<OrderResponseVendor> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<OrderResponseVendor>>> = api.fetchOrdersVendor(userId, "vendor")
        }.asLiveData()
    }

    fun updateStatus(updateStatus: UpdateStatusRequest): LiveData<Resource<BaseResponsePostRequest>> {
        return object : NetworkServiceWrapper<BaseResponsePostRequest, BaseResponsePostRequest>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponsePostRequest): LiveData<BaseResponsePostRequest> {
                return InitialLiveData.create(data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponsePostRequest>> =
                api.updateOrderStatus(updateStatus.id, updateStatus.userType, updateStatus.status,
                updateStatus.orderId, updateStatus.orderedProductId)
        }.asLiveData()
    }
}