package com.cmpe352group4.buyo.datamanager.network.interceptors

import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import okhttp3.Interceptor
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton

private const val ACCEPT_HEADER = "accept"
private const val JSON_TYPE = "*/*"
@Singleton
class HeaderInterceptor @Inject constructor(
    // We can add some general info(if backend wants us to add) into header via sharedPref
    private val sharedPref: SharedPref
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request: Request = chain.request()
        request = request.newBuilder().addHeader(ACCEPT_HEADER, JSON_TYPE)
            .build()


        return chain.proceed(request)
    }
}