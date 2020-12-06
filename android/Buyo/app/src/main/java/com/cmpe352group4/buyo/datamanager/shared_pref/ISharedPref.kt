package com.cmpe352group4.buyo.datamanager.shared_pref

import com.google.android.gms.maps.model.Marker

interface ISharedPref {

    // Example

//    fun saveToken(token: String?)
//    fun getToken(): String?
//
    fun saveUserId(id: String)
    fun getUserId(): String?
    fun saveVendorLat(lat: String)
    fun saveVendorLon(lon: String)
    fun getVendorLat(): String?
    fun getVendorLon(): String?
}