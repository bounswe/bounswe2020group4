package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.MessagesRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.LastMessageRequest
import com.cmpe352group4.buyo.vo.LastMessageResponse
import com.cmpe352group4.buyo.vo.LiveChatMessagesRequest
import com.cmpe352group4.buyo.vo.LiveChatMessagesResponse
import javax.inject.Inject


class MessagesViewModel @Inject constructor(
    val repository: MessagesRepository
) : ViewModel() {

    private val _userInfo = MutableLiveData<LastMessageRequest>()
    private val _liveChat = MutableLiveData<LiveChatMessagesRequest>()

    val lastMessages: LiveData<Resource<LastMessageResponse>> =
        Transformations.switchMap(_userInfo) { it ->
            if (it == null)
                AbsentLiveData.create()
            else
                repository.getLastMessages(it.userType, it.id)
        }

    val liveChatMessages: LiveData<Resource<LiveChatMessagesResponse>> =
        Transformations.switchMap(_liveChat) { it ->
            if (it == null)
                AbsentLiveData.create()
            else
                repository.getLiveChatMessages(it.userType, it.id, it.withType, it.withId)
        }

    fun onFetchLastMessages(userInfo: LastMessageRequest) {
        if (_userInfo.value == null) {
            _userInfo.value = userInfo
        }
    }

    fun onFetchLiveChatMessages(liveChat: LiveChatMessagesRequest) {
        _liveChat.value = liveChat
    }

}