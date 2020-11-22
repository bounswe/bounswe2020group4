package com.cmpe352group4.buyo.datamanager.shared_pref

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

//private const val TOKEN = "Token"
private const val USER_ID = "user_id"


@Singleton
class SharedPref @Inject constructor(
    private val sharedPref: SharedPreferences
) : ISharedPref {

    // Example
//    override fun saveToken(token: String?) {
//        val editor = sharedPref.edit()
//        editor.putString(TOKEN, token).apply()
//    }
//
//    override fun getToken(): String? {
//        return sharedPref.getString(TOKEN, null)
//    }
//
    override fun saveUserId(id: String) {
        val editor = sharedPref.edit()
        editor.putString(USER_ID, id).apply()
    }

    override fun getUserId(): String? = sharedPref.getString(USER_ID, null)


}