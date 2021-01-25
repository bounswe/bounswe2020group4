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
class MessagesRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val api: Api,
    private val connectionManager: ConnectionManager
){

    fun getLastMessages(userType: String, id: String): LiveData<Resource<LastMessageResponse>> {
        return object : NetworkServiceWrapper<LastMessageResponse, BaseResponse<LastMessageResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<LastMessageResponse>): LiveData<LastMessageResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<LastMessageResponse>>> = api.fetchLastMessages(userType = userType, id = id)
        }.asLiveData()
    }

    fun getLiveChatMessages(userType: String, id: String, withType: String, withId: String): LiveData<Resource<LiveChatMessagesResponse>> {
        return object : NetworkServiceWrapper<LiveChatMessagesResponse, BaseResponse<LiveChatMessagesResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<LiveChatMessagesResponse>): LiveData<LiveChatMessagesResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<LiveChatMessagesResponse>>> = api.fetchLiveChatMessages(userType = userType, id = id, withType = withType, withId = withId)
        }.asLiveData()
    }

    fun sendMessage(sendMessageRequest: SendMessageRequest): LiveData<Resource<SendMessageResponse>> {
        return object : NetworkServiceWrapper<SendMessageResponse, BaseResponse<SendMessageResponse>>(appExecutors,connectionManager){
            override fun loadFromApi(data: BaseResponse<SendMessageResponse>): LiveData<SendMessageResponse> {
                return InitialLiveData.create(data.data)
            }
            override fun createCall(): LiveData<ApiResponse<BaseResponse<SendMessageResponse>>> = api.sendMessage(sendMessageRequest)
        }.asLiveData()
    }

}