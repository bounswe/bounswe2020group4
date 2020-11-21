package com.cmpe352group4.buyo.util

import android.content.Context

class DialogManager :IDialogManager{

    private var dialog: LoadingDialog? = null

    override fun loading(context: Context) {
        try {
            dispatchLoading()
            dialog = LoadingDialog(context)
            dialog?.setCancelable(false)
            dialog?.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun dispatchLoading() {
        try {
            dialog?.dismiss()
            dialog = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}