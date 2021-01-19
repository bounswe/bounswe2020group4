package com.cmpe352group4.buyo.datamanager.repositories

import androidx.lifecycle.LiveData
import com.cmpe352group4.buyo.api.Api
import com.cmpe352group4.buyo.api.ApiResponse
import com.cmpe352group4.buyo.api.NetworkServiceWrapper
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.base.AppExecutors
import com.cmpe352group4.buyo.base.ConnectionManager
import com.cmpe352group4.buyo.util.livedata.InitialLiveData
import com.cmpe352group4.buyo.vo.BaseResponse
import com.cmpe352group4.buyo.vo.BaseResponsePostRequest
import com.cmpe352group4.buyo.vo.WishListProducts
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishListRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val api: Api,
    private val connectionManager: ConnectionManager
){

    fun getWishListProducts(customerId: String): LiveData<Resource<WishListProducts>> {
        return object : NetworkServiceWrapper<WishListProducts, BaseResponse<WishListProducts>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<WishListProducts>): LiveData<WishListProducts> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<WishListProducts>>> = api.fetchWishList(customerId)
        }.asLiveData()
    }

    fun unlikeProduct(customerId: String, productId: String): LiveData<Resource<BaseResponsePostRequest>> {
        return object : NetworkServiceWrapper<BaseResponsePostRequest, BaseResponsePostRequest>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponsePostRequest): LiveData<BaseResponsePostRequest> {
                return InitialLiveData.create(data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponsePostRequest>> = api.unlikeProductWishList(customerId, productId)
        }.asLiveData()
    }

}