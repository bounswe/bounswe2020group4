package com.cmpe352group4.buyo.datamanager.shared_pref

import android.content.SharedPreferences
import com.google.android.gms.maps.model.Marker
import javax.inject.Inject
import javax.inject.Singleton

//private const val TOKEN = "Token"
private const val USER_ID = "user_id"
private const val USER_TYPE = "user_type"
private const val LOC_LAT = "loc_lat"
private const val LOC_LON = "loc_lon"
private const val LOC_ADDRESS = "loc_address"
private const val REMEMBER_ME = "remember_me"
private const val VERIFIED = "verified"
private const val IS_GOOGLE_SIGNIN = "is_google_signin"

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

    override fun getUserType(): String? = sharedPref.getString(USER_TYPE, "")

    override fun saveUserType(type: String) {
        val editor = sharedPref.edit()
        editor.putString(USER_TYPE, type).apply()
    }

    override fun getUserId(): String? = sharedPref.getString(USER_ID, null)

    override fun saveVendorLat(lat: String) {
        val editor = sharedPref.edit()
        editor.putString(LOC_LAT, lat).apply()
    }
    override fun saveVendorLon(lon: String) {
        val editor = sharedPref.edit()
        editor.putString(LOC_LON, lon).apply()
    }
    override fun saveVendorAddress(address: String) {
        val editor = sharedPref.edit()
        editor.putString(LOC_ADDRESS, address).apply()
    }
    override fun getVendorLat(): String? = sharedPref.getString(LOC_LAT, null)
    override fun getVendorLon(): String? = sharedPref.getString(LOC_LON, null)
    override fun getVendorAddress(): String? = sharedPref.getString(LOC_ADDRESS, null)

    override fun saveRememberMe(boolean: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean(REMEMBER_ME, boolean).apply()
    }

    override fun getRememberMe(): Boolean = sharedPref.getBoolean(REMEMBER_ME, false)

    override fun saveVerified(boolean: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean(VERIFIED, boolean).apply()
    }

    override fun getVerified(): Boolean = sharedPref.getBoolean(VERIFIED, false)

    override fun saveIsGoogleSignin(boolean: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean(IS_GOOGLE_SIGNIN, boolean).apply()
    }

    override fun isGoogleSignin(): Boolean = sharedPref.getBoolean(IS_GOOGLE_SIGNIN, false)
}