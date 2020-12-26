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
import com.cmpe352group4.buyo.vo.ProductResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val api: Api,
    private val connectionManager: ConnectionManager
){

    fun getProductsbyKeyword(keyword:String): LiveData<Resource<ProductResponse>> {
        return object : NetworkServiceWrapper<ProductResponse, BaseResponse<ProductResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<ProductResponse>): LiveData<ProductResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<ProductResponse>>> = api.fetchSearchResult(keyword)
        }.asLiveData()
    }

    fun getProductsbyCategory(categoryList: String) : LiveData<Resource<ProductResponse>> {
        return object : NetworkServiceWrapper<ProductResponse, BaseResponse<ProductResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<ProductResponse>): LiveData<ProductResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<ProductResponse>>> = api.fetchProductsbyCategory(categoryList)
        }.asLiveData()
    }

}