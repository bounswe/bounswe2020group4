package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.ProfileRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.CustomerInformationResult
import com.cmpe352group4.buyo.vo.UserInformationRequest
import javax.inject.Inject

class ProfileInfoViewModel @Inject constructor(
    val repository: ProfileRepository
) : ViewModel() {

    private val _userInformationRequest = MutableLiveData<UserInformationRequest>()

    val userInformation: LiveData<Resource<CustomerInformationResult>> =
        Transformations.switchMap(_userInformationRequest) { it ->
            if (it == null)
                AbsentLiveData.create()
            else
                repository.getProfileInfo(it.id, it.userType)
        }

    fun onFetchProfileInfo(update: UserInformationRequest) {
        _userInformationRequest.value = update
    }
}