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
    private val _updateStatus =  MutableLiveData<UpdateStatusRequest>()

    val orderMap: LiveData<Resource<OrderResponse>> =
        Transformations.switchMap(_userId) { Id ->
            if (Id == null)
                AbsentLiveData.create()
            else
                repository.getOrders(Id)
        }

    val orderMapVendor: LiveData<Resource<OrderResponseVendor>> =
        Transformations.switchMap(_userIdVendor) { Id ->
            if (Id == null)
                AbsentLiveData.create()
            else
                repository.getOrdersVendor(Id)
        }

    val updateStatus: LiveData<Resource<BaseResponsePostRequest>> =
        Transformations.switchMap(_updateStatus) { updateStatusRequestObject ->
            if (updateStatusRequestObject == null)
                AbsentLiveData.create()
            else
                repository.updateStatus(updateStatusRequestObject)
        }

    fun onFetchOrders(userId: String) {
        _userId.value = userId
    }

    fun onFetchOrdersVendor(userId: String) {
        _userIdVendor.value = userId
    }

    fun onUpdateStatus(userId: String, orderId: String, orderedProductId: String, userType: String, newStatus: String) {
        _updateStatus.value = UpdateStatusRequest(userId, userType, newStatus, orderId, orderedProductId)
    }

}