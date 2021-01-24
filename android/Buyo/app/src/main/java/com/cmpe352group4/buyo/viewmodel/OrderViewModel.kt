package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.OrderRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.*
import javax.inject.Inject

class OrderViewModel @Inject constructor(
    val repository: OrderRepository
) : ViewModel() {


    private val _userId =  MutableLiveData<String>()
    private val _userIdVendor =  MutableLiveData<String>()

    val orderMap: LiveData<Resource<Map<String, Order>>> =
        Transformations.switchMap(_userId) { Id ->
            if (Id == null)
                AbsentLiveData.create()
            else
                repository.getOrders(Id)
        }

    val orderMapVendor: LiveData<Resource<Map<String, OrderVendor>>> =
        Transformations.switchMap(_userIdVendor) { Id ->
            if (Id == null)
                AbsentLiveData.create()
            else
                repository.getOrdersVendor(Id)
        }

    fun onFetchOrders(userId: String) {
        _userId.value = userId
    }

    fun onFetchOrdersVendor(userId: String) {
        _userIdVendor.value = userId
    }

}