package com.cmpe352group4.buyo.datamanager.network

import androidx.lifecycle.LiveData
import com.cmpe352group4.buyo.api.ApiResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LiveDataCallAdapterFactory @Inject constructor(): CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java)
            return null
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)

        if (rawObservableType != ApiResponse::class.java)
            throw IllegalArgumentException("type must be a resource")

        if (observableType !is ParameterizedType)
            throw IllegalArgumentException("resource must be a parameterized")

        val bodyType = getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }
}