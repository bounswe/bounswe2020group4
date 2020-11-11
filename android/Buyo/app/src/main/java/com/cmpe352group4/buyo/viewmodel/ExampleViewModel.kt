package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.ExampleRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.Product
import javax.inject.Inject

class ExampleViewModel @Inject constructor(
    val repository: ExampleRepository
) : ViewModel() {

    private val _productId =  MutableLiveData<Int>()

    val productDetail: LiveData<Resource<Product>> =
        Transformations.switchMap(_productId) { Id ->
            if (Id == null)
                AbsentLiveData.create()
            else
                repository.getProductbyId(Id)
        }

    fun onFetchProductById(expertId: Int) {
        _productId.value = expertId
    }

}