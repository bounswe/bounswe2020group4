package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.SearchRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.ProductResponse
import com.cmpe352group4.buyo.vo.SearchCategoryRequest
import com.cmpe352group4.buyo.vo.SearchKeywordRequest
import javax.inject.Inject


class SearchViewModel @Inject constructor(
    val repository: SearchRepository
) : ViewModel() {

    private val _keyword = MutableLiveData<SearchKeywordRequest>()

    private val _categoryList = MutableLiveData<SearchCategoryRequest>()

    val searchResult: LiveData<Resource<ProductResponse>> =
        Transformations.switchMap(_keyword) { request->
            if ( request == null)
                AbsentLiveData.create()
            else
                repository.getProductsbyKeyword(request.keyword, request.filterSort)
        }

    val categoryResult: LiveData<Resource<ProductResponse>> =
        Transformations.switchMap(_categoryList) { request ->
            if ( request == null)
                AbsentLiveData.create()
            else
                repository.getProductsbyCategory(request.categories, request.filterSort)
        }

    fun onFetchSearchResultbyKeyword(keyword: String, options: Map<String,String>?) {
        if (_keyword.value == null) {
            _keyword.value = SearchKeywordRequest(keyword=keyword, filterSort = options)
        }
    }


    fun onFetchSearchResultbyCategory(category: String, options: Map<String,String>?) {
        if (_categoryList.value == null) {
            _categoryList.value = SearchCategoryRequest(categories = category, filterSort = options)
        }
    }

}