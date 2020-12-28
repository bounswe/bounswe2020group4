package com.cmpe352group4.buyo.util

import android.content.Context

interface IDialogManager {
    fun loading(context: Context)
    fun dispatchLoading()
}