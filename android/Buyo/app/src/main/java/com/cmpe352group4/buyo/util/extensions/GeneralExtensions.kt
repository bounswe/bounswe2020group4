package com.cmpe352group4.buyo.util.extensions

import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions

fun hideKeyboardFrom(context: Context?, windowToken: IBinder?) {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

 // It doesn't find GlideApp, find out the problem later

//fun ImageView.loadFromURL(url: String) {
//    GlideApp.with(this)
//        .load(url)
//        .centerCrop()
//        .fitCenter()
//        .into(this)
//}
//
//fun ImageView.loadFromURLWithCircle(url: String) {
//    GlideApp.with(this)
//        .load(url)
//        .apply(RequestOptions.circleCropTransform())
//        .into(this)
//}