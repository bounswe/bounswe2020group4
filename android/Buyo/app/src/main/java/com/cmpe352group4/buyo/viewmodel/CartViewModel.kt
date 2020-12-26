package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.CartRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.Cart
import com.cmpe352group4.buyo.vo.CheckoutRequest
import com.cmpe352group4.buyo.vo.CheckoutResponse
import javax.inject.Inject

class CartViewModel @Inject constructor(
    val repository: CartRepository
) : ViewModel() {


    private val _userId =  MutableLiveData<String>()
    private val _checkoutRequest =  MutableLiveData<CheckoutRequest>()

    val cartInfo: LiveData<Resource<Cart>> =
        Transformations.switchMap(_userId) { Id ->
            if (Id == null)
                AbsentLiveData.create()
            else
                repository.getCartInfo(Id)
        }

    val checkoutResponse: LiveData<Resource<CheckoutResponse>> =
        Transformations.switchMap(_checkoutRequest) { it ->
            if (it == null)
                AbsentLiveData.create()
            else
                repository.checkout(it)
        }


    fun onFetchCartInfo(userId: String) {
        _userId.value = userId
    }

    fun onCheckout(checkoutRequest: CheckoutRequest) {
        _checkoutRequest.value = checkoutRequest
    }

}