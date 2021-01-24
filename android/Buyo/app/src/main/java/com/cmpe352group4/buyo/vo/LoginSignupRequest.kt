package com.cmpe352group4.buyo.vo

import kotlinx.android.synthetic.main.fragment_login_vendor.*
import java.io.Serializable

data class LoginRequestCustomer(
    var userType: String,
    var email: String,
    var password: String
): Serializable

data class LoginRequestVendor(
    var userType: String,
    var email: String,
    var password: String
): Serializable

data class SignupRequestCustomer(
    var userType: String,
    var email: String,
    var password: String
): Serializable

data class SignupRequestVendor(
    var userType: String,
    var email: String,
    var password: String,
    var longitude: String,
    var latitude: String,
    var website: String,
    var company: String
): Serializable

data class ForgotPasswordRequest(
    var email: String
): Serializable