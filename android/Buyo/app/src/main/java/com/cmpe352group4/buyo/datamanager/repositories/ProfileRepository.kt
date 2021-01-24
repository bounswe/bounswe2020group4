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
class ProfileRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val api: Api,
    private val connectionManager: ConnectionManager
){

    fun loginCustomer(userType: String, email: String, password: String): LiveData<Resource<LoginSingupResponse>> {
        return object : NetworkServiceWrapper<LoginSingupResponse, BaseResponse<LoginSingupResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<LoginSingupResponse>): LiveData<LoginSingupResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<LoginSingupResponse>>> = api.login_customer(userType, email, password)
        }.asLiveData()
    }

    fun singupCustomer(userType: String, email: String, password: String): LiveData<Resource<LoginSingupResponse>> {
        return object : NetworkServiceWrapper<LoginSingupResponse, BaseResponse<LoginSingupResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<LoginSingupResponse>): LiveData<LoginSingupResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<LoginSingupResponse>>> = api.signup_customer(userType, email, password)
        }.asLiveData()
    }

    fun googleSignIn(email: String, name: String, token: String): LiveData<Resource<LoginSingupResponse>> {
        return object : NetworkServiceWrapper<LoginSingupResponse, BaseResponse<LoginSingupResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<LoginSingupResponse>): LiveData<LoginSingupResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<LoginSingupResponse>>> = api.googleSignIn(email, name, token)
        }.asLiveData()
    }

    fun getProfileInfo(customerId: String, userType: String): LiveData<Resource<CustomerInformationResult>> {
        return object : NetworkServiceWrapper<CustomerInformationResult,BaseResponse<CustomerInformationResult>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<CustomerInformationResult>): LiveData<CustomerInformationResult> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<CustomerInformationResult>>> = api.fetchAccountInformation(customerId, userType)
        }.asLiveData()
    }

    fun getVendorProfileInfo(customerId: String, userType: String): LiveData<Resource<VendorInformationResult>> {
        return object : NetworkServiceWrapper<VendorInformationResult,BaseResponse<VendorInformationResult>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<VendorInformationResult>): LiveData<VendorInformationResult> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<VendorInformationResult>>> = api.fetchVendorAccountInformation(customerId, userType)
        }.asLiveData()
    }

    fun loginVendor(userType: String, email: String, password: String): LiveData<Resource<LoginSingupResponse>> {
        return object : NetworkServiceWrapper<LoginSingupResponse, BaseResponse<LoginSingupResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<LoginSingupResponse>): LiveData<LoginSingupResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<LoginSingupResponse>>> = api.login_vendor(userType, email, password)
        }.asLiveData()
    }

    fun singupVendor(userType: String, email: String, password: String, latitude: String,
                     longitude: String, website: String, company: String): LiveData<Resource<LoginSingupResponse>> {
        return object : NetworkServiceWrapper<LoginSingupResponse, BaseResponse<LoginSingupResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<LoginSingupResponse>): LiveData<LoginSingupResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<LoginSingupResponse>>> =
                api.signup_vendor(userType, email, password, latitude, longitude, website, company)
        }.asLiveData()
    }

    fun changePassword(id: String, userType: String, password: String): LiveData<Resource<BaseResponsePostRequest>> {
        return object : NetworkServiceWrapper<BaseResponsePostRequest, BaseResponsePostRequest>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponsePostRequest): LiveData<BaseResponsePostRequest> {
                return InitialLiveData.create(data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponsePostRequest>> = api.changePassword(id, userType, password)
        }.asLiveData()
    }
  
    fun addAddress(addAddressRequest: AddAddressRequest): LiveData<Resource<BaseResponsePostRequest>> {
        return object : NetworkServiceWrapper<BaseResponsePostRequest, BaseResponsePostRequest>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponsePostRequest): LiveData<BaseResponsePostRequest> {
                return InitialLiveData.create(data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponsePostRequest>> = api.addAddress(addAddressRequest.id, addAddressRequest.address)
        }.asLiveData()
    }

    fun saveAccountInfo(id: String, userType: String, name: String, surname: String, email: String,
                        phoneNumber: String, gender: String): LiveData<Resource<BaseResponsePostRequest>> {
        return object : NetworkServiceWrapper<BaseResponsePostRequest, BaseResponsePostRequest>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponsePostRequest): LiveData<BaseResponsePostRequest> {
                return InitialLiveData.create(data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponsePostRequest>> =
                api.saveAccountInfo(id, userType, name, surname, email, phoneNumber, gender)
        }.asLiveData()
    }

    fun saveVendorAccountInfo(id: String, userType: String, email: String, longitude: String, latitude: String,
                        website: String, company: String): LiveData<Resource<BaseResponsePostRequest>> {
        return object : NetworkServiceWrapper<BaseResponsePostRequest, BaseResponsePostRequest>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponsePostRequest): LiveData<BaseResponsePostRequest> {
                return InitialLiveData.create(data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponsePostRequest>> =
                api.saveVendorAccountInfo(id, userType, email, longitude, latitude, website, company)
        }.asLiveData()
    }
}

