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
class NotificationRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val api: Api,
    private val connectionManager: ConnectionManager
){

    fun getNotifications(userId:String, userType:String): LiveData<Resource<NotificationResponse>> {
        return object : NetworkServiceWrapper<NotificationResponse, BaseResponse<NotificationResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<NotificationResponse>): LiveData<NotificationResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<NotificationResponse>>> = api.fetchNotifications(userType, userId)
        }.asLiveData()
    }
}