package com.cmpe352group4.buyo.datamanager.repositories

import androidx.lifecycle.LiveData
import com.cmpe352group4.buyo.api.Api
import com.cmpe352group4.buyo.api.ApiResponse
import com.cmpe352group4.buyo.api.NetworkServiceWrapper
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.base.AppExecutors
import com.cmpe352group4.buyo.base.ConnectionManager
import com.cmpe352group4.buyo.util.livedata.InitialLiveData
import com.cmpe352group4.buyo.vo.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val api: Api,
    private val connectionManager: ConnectionManager
){

    fun getCategories(): LiveData<Resource<List<Category>>> {
        return object : NetworkServiceWrapper<List<Category>, List<Category>>(appExecutors,connectionManager){
            override fun loadFromApi(data: List<Category>): LiveData<List<Category>> {
                return InitialLiveData.create(data)
            }
            override fun createCall(): LiveData<ApiResponse<List<Category>>> = api.fetchCategories()
        }.asLiveData()
    }
}