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
import com.cmpe352group4.buyo.vo.LoginSingupResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val api: Api,
    private val connectionManager: ConnectionManager
){

    fun loginUser(userType: String, email: String, password: String): LiveData<Resource<LoginSingupResponse>> {
        return object : NetworkServiceWrapper<LoginSingupResponse, BaseResponse<LoginSingupResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<LoginSingupResponse>): LiveData<LoginSingupResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<LoginSingupResponse>>> = api.login(userType, email, password)
        }.asLiveData()
    }

    fun singupUser(userType: String, email: String, password: String): LiveData<Resource<LoginSingupResponse>> {
        return object : NetworkServiceWrapper<LoginSingupResponse, BaseResponse<LoginSingupResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<LoginSingupResponse>): LiveData<LoginSingupResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<LoginSingupResponse>>> = api.signup(userType, email, password)
        }.asLiveData()
    }

}