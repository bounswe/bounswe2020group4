package com.cmpe352group4.buyo.api

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.cmpe352group4.buyo.base.AppExecutors
import com.cmpe352group4.buyo.base.ConnectionManager
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData

abstract class NetworkServiceWrapper<ResultType, RequestType>
@MainThread constructor(
    private val appExecutors: AppExecutors,
    connectionManager: ConnectionManager
) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        if (connectionManager.hasConnection(::fetchFromNetwork)) {
            result.value = Resource.loading(null)
            fetchFromNetwork()
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.mainThread().execute {
                        result.addSource(loadFromApi(response.body)) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    onFetchEmpty()
                    appExecutors.mainThread().execute {
                        result.addSource(fail()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                is ApiErrorResponse -> {
                    appExecutors.mainThread().execute {
                        result.addSource(fail()) { error ->
                            setValue(Resource.error(response.errorMessage, error))
                        }
                    }
                    onFetchFailed()
                }
            }
        }
    }

    private fun fail(): LiveData<ResultType> {
        return AbsentLiveData.create()
    }

    protected open fun onFetchFailed() {}

    protected open fun onFetchEmpty() {
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>


    @MainThread
    protected abstract fun loadFromApi(data: RequestType): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}