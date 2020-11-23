package com.cmpe352group4.buyo.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.cmpe352group4.buyo.dependencyinjection.ApplicationContext
import com.cmpe352group4.buyo.util.GeneralUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var activity: AppCompatActivity? = null
    private var activityStarted = false
    private var _requestList = mutableListOf<() -> Unit>()
    fun setActivity(activity: AppCompatActivity?) {
        this.activity = activity
    }

    // Some functionality can be added if connection handling needed

    fun hasConnection(req: () -> Unit): Boolean {
        val connection = checkConnection()
        if (!connection) {
            _requestList.add(req)
//            activity?.let {
//                if (!activityStarted) {
//                    NoConnectionActivity.newInstance(it)
//                    activityStarted = true
//                }
//            }
        }
        return connection
    }

    fun changeActivityState(state: Boolean) {
        activityStarted = state
    }

    fun checkConnection(): Boolean {
        return GeneralUtil.checkNetworkConnection(context)
    }

    fun clearRequests() {
        _requestList.clear()
    }

    fun requestList() = _requestList
}
