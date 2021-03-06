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
class VendorRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val api: Api,
    private val connectionManager: ConnectionManager
){

    fun uploadImage(image:ByteArray): LiveData<Resource<UploadImageResponse>> {
        return object : NetworkServiceWrapper<UploadImageResponse,BaseResponse<UploadImageResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<UploadImageResponse>): LiveData<UploadImageResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<UploadImageResponse>>> = api.uploadImage(image)
        }.asLiveData()
    }

    fun getProducts(vendorID : String): LiveData<Resource<VendorProductResponseResult>> {
        return object : NetworkServiceWrapper<VendorProductResponseResult, BaseResponse<VendorProductResponseResult>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<VendorProductResponseResult>): LiveData<VendorProductResponseResult> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<VendorProductResponseResult>>> = api.getVendorProducts(vendorID)
        }.asLiveData()
    }

    fun addProduct(vendorID: String, products : List<AddProduct>): LiveData<Resource<AddProductResponseResult>> {
        return object : NetworkServiceWrapper<AddProductResponseResult, BaseResponse<AddProductResponseResult>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<AddProductResponseResult>): LiveData<AddProductResponseResult> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<AddProductResponseResult>>> = api.addProduct(vendorID, products)
        }.asLiveData()
    }

    fun updateProduct(productID: String, product : Product): LiveData<Resource<EditProductResponseResult>> {
        return object : NetworkServiceWrapper<EditProductResponseResult, BaseResponse<EditProductResponseResult>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<EditProductResponseResult>): LiveData<EditProductResponseResult> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<EditProductResponseResult>>> = api.updateProduct(productID, product)
        }.asLiveData()
    }

    fun deleteProduct(vendorID: String, productID : String): LiveData<Resource<BaseResponsePostRequest>> {
        return object : NetworkServiceWrapper<BaseResponsePostRequest, BaseResponsePostRequest>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponsePostRequest): LiveData<BaseResponsePostRequest> {
                return InitialLiveData.create(data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponsePostRequest>> = api.deleteProduct(vendorID, DeleteProductRequest(productId = productID))
        }.asLiveData()
    }


}