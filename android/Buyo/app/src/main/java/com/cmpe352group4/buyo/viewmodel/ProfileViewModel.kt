package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.ProfileRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.CustomerInformationResult
import com.cmpe352group4.buyo.vo.LoginSignupRequest
import com.cmpe352group4.buyo.vo.LoginSingupResponse
import com.cmpe352group4.buyo.vo.UserInformationRequest
import javax.inject.Inject


class ProfileViewModel @Inject constructor(
    val repository: ProfileRepository
) : ViewModel() {


    private val _loginRequest = MutableLiveData<LoginSignupRequest>()
    private val _singupRequest = MutableLiveData<LoginSignupRequest>()
    private val _userInformationRequest = MutableLiveData<UserInformationRequest>()

    val userInformation: LiveData<Resource<CustomerInformationResult>> =
        Transformations.switchMap(_userInformationRequest) { it ->
            if (it == null)
                AbsentLiveData.create()
            else
                repository.getProfileInfo(it.id, it.userType)
        }

    val login: LiveData<Resource<LoginSingupResponse>> =
        Transformations.switchMap(_loginRequest) { it ->
            if (it == null)
                AbsentLiveData.create()
            else
                repository.loginUser(it.userType, it.email, it.password)
        }

    val singup: LiveData<Resource<LoginSingupResponse>> =
        Transformations.switchMap(_singupRequest) { it ->
            if (it == null)
                AbsentLiveData.create()
            else
                repository.singupUser(it.userType, it.email, it.password)
        }

    fun onLogin(login: LoginSignupRequest) {
        _loginRequest.value = login
    }

    fun onSingup(signUp: LoginSignupRequest) {
        _singupRequest.value = signUp
    }

    fun onFetchProfileInfo(update: UserInformationRequest) {
        _userInformationRequest.value = update
    }

}