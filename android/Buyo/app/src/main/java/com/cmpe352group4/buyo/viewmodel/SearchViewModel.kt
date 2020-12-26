package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.SearchRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.Product
import com.cmpe352group4.buyo.vo.ProductList
import javax.inject.Inject


class SearchViewModel @Inject constructor(
    val repository: SearchRepository
) : ViewModel() {

    private val _keyword = MutableLiveData<String>()

    private val _categoryList = MutableLiveData<String>()

    val searchResult: LiveData<Resource<ProductList>> =
        Transformations.switchMap(_keyword) { keyword ->
            if ( keyword == null)
                AbsentLiveData.create()
            else
                repository.getProductsbyKeyword(keyword)
        }

    val categoryResult: LiveData<Resource<ProductList>> =
        Transformations.switchMap(_categoryList) { categoryList ->
            if ( categoryList == null)
                AbsentLiveData.create()
            else
                repository.getProductsbyCategory(categoryList)
        }

    fun onFetchSearchResultbyKeyword(isFetch: String) {
        if (_keyword.value == null) {
            _keyword.value = isFetch
        }
    }


    fun onFetchSearchResultbyCategory(isFetch: String) {
        if (_categoryList.value == null) {
            _categoryList.value = isFetch
        }
    }

}