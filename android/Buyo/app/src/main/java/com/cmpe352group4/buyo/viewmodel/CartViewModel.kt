package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.CartRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.*
import javax.inject.Inject

class CartViewModel @Inject constructor(
    val repository: CartRepository
) : ViewModel() {


    private val _userId =  MutableLiveData<String>()
    private val _checkoutRequest =  MutableLiveData<CheckoutRequest>()
    private val _addCartRequest = MutableLiveData<AddCartRequest>()
    private val _removeCartRequest = MutableLiveData<RemoveCartRequest>()

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

    val addCartResponse: LiveData<Resource<BaseResponsePostRequest>> =
        Transformations.switchMap(_addCartRequest) { it ->
            if (it == null )
                AbsentLiveData.create()
            else
                repository.add2cart(it.customerId, it.productId, it.productInfo)
        }

    val removeCartResponse: LiveData<Resource<BaseResponsePostRequest>> =
        Transformations.switchMap(_removeCartRequest) { it ->
            if (it == null )
                AbsentLiveData.create()
            else
                repository.removeFromCart(it.customerId, it.productId, it.productInfo)
        }


    fun onFetchCartInfo(userId: String) {
        _userId.value = userId
    }

    fun onCheckout(checkoutRequest: CheckoutRequest) {
        _checkoutRequest.value = checkoutRequest
    }

    fun onAdd(add : AddCartRequest){
        _addCartRequest.value = add
    }

    fun onRemove(remove : RemoveCartRequest){
        _removeCartRequest.value = remove
    }

}