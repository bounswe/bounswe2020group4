package com.cmpe352group4.buyo.vo

import java.io.Serializable
/*
data class Notification (
    // for the endpoint
):Serializable
*/


data class NotificationRV (
    var summary: String,    //  notification message
    var name: String,       // Discount or Cancel Order
    var time: String,       // Format: 2020-12-28T08:08:37.507Z
    var targetProductID: String? // Null if notification is not related with a product
): Serializable
