package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.CartRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.Cart
import javax.inject.Inject

class CartViewModel @Inject constructor(
    val repository: CartRepository
) : ViewModel() {


    private val _userId =  MutableLiveData<String>()

    val cartInfo: LiveData<Resource<Cart>> =
        Transformations.switchMap(_userId) { Id ->
            if (Id == null)
                AbsentLiveData.create()
            else
                repository.getCartInfo(Id)
        }


    fun onFetchCartInfo(userId: String) {
        _userId.value = userId
    }

}