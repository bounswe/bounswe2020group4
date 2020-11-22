package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.CategoryRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.CategoryList
import javax.inject.Inject


class CategoryViewModel @Inject constructor(
    val repository: CategoryRepository
) : ViewModel() {

    private val _category = MutableLiveData<Boolean>()

    val categories: LiveData<Resource<CategoryList>> =
        Transformations.switchMap(_category) { Id ->
            if (Id == null)
                AbsentLiveData.create()
            else
                repository.getCategories()
        }

    fun onFetchCategories(isFetch: Boolean) {
        if (_category.value == null) {
            _category.value = isFetch
        }
    }

}