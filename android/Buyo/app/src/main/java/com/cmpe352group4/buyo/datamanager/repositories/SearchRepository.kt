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
import com.cmpe352group4.buyo.vo.Product
import com.cmpe352group4.buyo.vo.ProductList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val api: Api,
    private val connectionManager: ConnectionManager
){

    fun getProductsbyKeyword(keyword:String): LiveData<Resource<ProductList>> {
        return object : NetworkServiceWrapper<ProductList, BaseResponse<ProductList>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<ProductList>): LiveData<ProductList> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<ProductList>>> = api.fetchSearchResult(keyword)
        }.asLiveData()
    }

    fun getProductsbyCategory(categoryList: String) : LiveData<Resource<ProductList>> {
        return object : NetworkServiceWrapper<ProductList, BaseResponse<ProductList>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<ProductList>): LiveData<ProductList> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<ProductList>>> = api.fetchProductsbyCategory(categoryList)
        }.asLiveData()
    }

}