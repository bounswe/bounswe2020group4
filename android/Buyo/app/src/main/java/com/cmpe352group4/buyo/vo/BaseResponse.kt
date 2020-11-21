package com.cmpe352group4.buyo.vo

data class BaseResponse<out T>(
    val data: T,
    val status: Status
)
