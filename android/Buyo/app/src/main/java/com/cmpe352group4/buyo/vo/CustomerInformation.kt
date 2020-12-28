package com.cmpe352group4.buyo.vo
import java.io.Serializable

data class CustomerInformation (
    var address: List<Address>?,
    var name: String?,
    var surname: String?,
    var email: String?,
    var phoneNumber: String?,
    var id: Int?,
    var password: String?,
    var gender: String?
): Serializable