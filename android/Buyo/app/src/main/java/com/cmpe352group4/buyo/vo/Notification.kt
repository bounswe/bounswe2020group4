package com.cmpe352group4.buyo.vo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NotificationResponse (
    var notifications: Notification
): Serializable

data class Notification (
    @SerializedName("@context")
    var context: String,
    var summary: String,
    var type: String,
    var totalItems: Int,
    var items: List<NotificationItem>
):Serializable

data class NotificationItem (
    var type: String,
    var name: String,
    var startTime: String,
    var summary: String,
    var actor: NotificationActor,
    var target: String
)

data class NotificationActor (
    var type: String,
    var name: String,
    var id: String
)

data class NotificationRV (
    var summary: String,    //  notification message
    var name: String,       // Discount or Cancel Order
    var time: String,       // Format: 2020-12-28T08:08:37.507Z
    var targetProductID: String? // Null if notification is not related with a product
): Serializable

