package com.cmpe352group4.buyo.util.livedata

import androidx.lifecycle.LiveData

class InitialLiveData<T : Any?> private constructor(data: T): LiveData<T>() {
    init {
        // use post instead of set since this can be created on any thread
        postValue(data)
    }

    companion object {
        fun <T> create(data: T): LiveData<T> {
            return InitialLiveData(data)
        }
    }
}