package com.cmpe352group4.buyo.util

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import com.cmpe352group4.buyo.R

class LoadingDialog : Dialog {

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.loading_dialog)
        val lp = WindowManager.LayoutParams()
        val wd = window
        if (wd != null)
            lp.copyFrom(wd.attributes)
        lp.width = 200
        lp.gravity = Gravity.CENTER
        lp.height = 200
        if (wd != null)
            wd.attributes = lp
        wd?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}