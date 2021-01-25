package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.NotificationRepository
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.*
import javax.inject.Inject

class NotificationViewModel @Inject constructor(
    val repository: NotificationRepository
) : ViewModel() {

    private val _userId =  MutableLiveData<String>()

    @Inject
    lateinit var sharedPref: SharedPref

    val notifications: LiveData<Resource<NotificationResponse>> =
        Transformations.switchMap(_userId) { Id ->
            if (Id == null)
                AbsentLiveData.create()
            else
                repository.getNotifications(Id, sharedPref.getUserType()!!)
        }

    fun onFetchNotifications(userId: String, userType: String) {
        _userId.value = userId
    }
}