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
//import com.cmpe352group4.buyo.vo.ProductResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val api: Api,
    private val connectionManager: ConnectionManager
){

    fun getProductById(productId:String): LiveData<Resource<ProductResult>> {
        return object : NetworkServiceWrapper<ProductResult,BaseResponse<ProductResult>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<ProductResult>): LiveData<ProductResult> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<ProductResult>>> = api.fetchProductById(productId)
        }.asLiveData()
    }

    fun reportComment(commentId: String, message: String): LiveData<Resource<BaseResponse<ReportCommentResponse>>> {
        return object : NetworkServiceWrapper<BaseResponse<ReportCommentResponse>, BaseResponse<ReportCommentResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<ReportCommentResponse>): LiveData<BaseResponse<ReportCommentResponse>> {
                return InitialLiveData.create(data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<ReportCommentResponse>>> = api.reportComment(commentId, message)
        }.asLiveData()
    }

    fun reportProduct(productId: String, message: String): LiveData<Resource<BaseResponse<ReportProductResponse>>> {
        return object : NetworkServiceWrapper<BaseResponse<ReportProductResponse>, BaseResponse<ReportProductResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<ReportProductResponse>): LiveData<BaseResponse<ReportProductResponse>> {
                return InitialLiveData.create(data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<ReportProductResponse>>> = api.reportProduct(productId, message)
        }.asLiveData()
    }


    //fun getProductListbyKeyword(keyword:String): LiveData<Resource<ProductResponse>> {
    //    return object : NetworkServiceWrapper<ProductResponse,ProductResponse>(appExecutors,connectionManager){
    //        override fun loadFromApi(data: ProductResponse): LiveData<ProductResponse> {
    //            return InitialLiveData.create(data)
    //        }
    //        override fun createCall(): LiveData<ApiResponse<ProductResponse>> = api.fetchProductSearch(keyword)
    //    }.asLiveData()
    //}
}