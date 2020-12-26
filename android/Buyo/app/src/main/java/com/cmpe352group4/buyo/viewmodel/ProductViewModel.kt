package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.ProductRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
//import com.cmpe352group4.buyo.vo.ProductResponse
import com.cmpe352group4.buyo.vo.ProductResult
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    val repository: ProductRepository
) : ViewModel() {

    // Things we need to conduct a request

    private val _productId =  MutableLiveData<String>()

    //private val _searchKeyword = MutableLiveData<String>()

    // Things we get as a response
    val productDetail: LiveData<Resource<ProductResult>> =
        Transformations.switchMap(_productId) { Id ->
            if (Id == null)
                AbsentLiveData.create()
            else
                repository.getProductById(Id)
        }

    //val productList: LiveData<Resource<ProductResponse>> =
    //    Transformations.switchMap(_searchKeyword) { keyword ->
    //        if (keyword == null)
    //            AbsentLiveData.create()
    //        else
    //            repository.getProductListbyKeyword(keyword)
    //    }


    // Triggers to make a http request

    fun onFetchProductById(productId: String) {
        _productId.value = productId
    }

    //fun onFetchProductListbyKeyword(keyword: String) {
    //    _searchKeyword.value = keyword
    //}

}