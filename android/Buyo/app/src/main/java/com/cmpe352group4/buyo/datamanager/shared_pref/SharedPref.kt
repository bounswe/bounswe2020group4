package com.cmpe352group4.buyo.datamanager.shared_pref

import android.content.SharedPreferences
import com.google.android.gms.maps.model.Marker
import javax.inject.Inject
import javax.inject.Singleton

//private const val TOKEN = "Token"
private const val USER_ID = "user_id"
private var LOC_LAT: String? = null
private var LOC_LON: String? = null

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

    override fun saveVendorLoc(loc: Marker) {
        val editor = sharedPref.edit()
        editor.putString(LOC_LAT, loc.position.latitude.toString()).apply()
        editor.putString(LOC_LON, loc.position.longitude.toString()).apply()
    }
    override fun getVendorLat(): String? = sharedPref.getString(LOC_LAT, null)
    override fun getVendorLon(): String? = sharedPref.getString(LOC_LON, null)
}