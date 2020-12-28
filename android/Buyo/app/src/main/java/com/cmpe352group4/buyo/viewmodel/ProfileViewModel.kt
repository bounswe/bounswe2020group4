package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.ProfileRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.*
import javax.inject.Inject


class ProfileViewModel @Inject constructor(
    val repository: ProfileRepository
) : ViewModel() {

    private val _userInformationRequest = MutableLiveData<UserInformationRequest>()
    private val _loginRequestCustomer = MutableLiveData<LoginRequestCustomer>()
    private val _loginRequestVendor = MutableLiveData<LoginRequestVendor>()
    private val _singupRequestCustomer = MutableLiveData<SignupRequestCustomer>()
    private val _singupRequestVendor = MutableLiveData<SignupRequestVendor>()
    private val _changePasswordRequest = MutableLiveData<ChangePasswordRequest>()

    val changePassword: LiveData<Resource<BaseResponsePostRequest>> =
        Transformations.switchMap(_changePasswordRequest) {it ->
            if (it == null)
                AbsentLiveData.create()
            else
                repository.changePassword(it.id, it.userType, it.password)
        }

    val userInformation: LiveData<Resource<CustomerInformationResult>> =
        Transformations.switchMap(_userInformationRequest) { it ->
            if (it == null)
                AbsentLiveData.create()
            else
                repository.getProfileInfo(it.id, it.userType)
        }

    val loginCustomer: LiveData<Resource<LoginSingupResponse>> =
        Transformations.switchMap(_loginRequestCustomer) { it ->
            if (it == null)
                AbsentLiveData.create()
            else
                repository.loginCustomer(it.userType, it.email, it.password)
        }

    val singupCustomer: LiveData<Resource<LoginSingupResponse>> =
        Transformations.switchMap(_singupRequestCustomer) { it ->
            if (it == null)
                AbsentLiveData.create()
            else
                repository.singupCustomer(it.userType, it.email, it.password)
        }

    val loginVendor: LiveData<Resource<LoginSingupResponse>> =
        Transformations.switchMap(_loginRequestVendor) { it ->
            if (it == null)
                AbsentLiveData.create()
            else
                repository.loginVendor(it.userType, it.email, it.password)
        }

    val singupVendor: LiveData<Resource<LoginSingupResponse>> =
        Transformations.switchMap(_singupRequestVendor) { it ->
            if (it == null)
                AbsentLiveData.create()
            else
                repository.singupVendor(it.userType, it.email, it.password, it.longitude,
                                        it.latitude, it.website, it.company)
        }

    fun onLoginCustomer(login: LoginRequestCustomer) {
        _loginRequestCustomer.value = login
    }

    fun onSingupCustomer(signUp: SignupRequestCustomer) {
        _singupRequestCustomer.value = signUp
    }

    fun onLoginVendor(login: LoginRequestVendor) {
        _loginRequestVendor.value = login
    }

    fun onSingupVendor(signUp: SignupRequestVendor) {
        _singupRequestVendor.value = signUp
    }

    fun onFetchProfileInfo(update: UserInformationRequest) {
        _userInformationRequest.value = update
    }

    fun changePassword(update: ChangePasswordRequest) {
        _changePasswordRequest.value = update
    }

}