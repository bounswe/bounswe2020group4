package com.cmpe352group4.buyo.util

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.cmpe352group4.buyo.R

class GeneralUtil {
    companion object{

        fun checkNetworkConnection(context: Context?): Boolean {
            if (context == null)
                return false
            var result = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                result = when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                connectivityManager.run {
                    connectivityManager.activeNetworkInfo?.run {
                        result = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }

                    }
                }
            }

            return result
        }

        fun dialogWithOneOptions(
            context: Context,
            title: String,
            content: String,
            positiveText: String,
            negativeText: String,
            runnable: Runnable,
            cancelable: Boolean = true
        ) {
            val dialog = AlertDialog.Builder(context)
//            dialog.setTitle(title)
            dialog.setCancelable(cancelable)
            dialog.setMessage(content)
            dialog.setPositiveButton(
                positiveText
            ) { p0, _ ->
                runnable.run()
                p0.dismiss()
            }
            dialog.setNegativeButton(negativeText) { p0, _ ->
                p0.dismiss()
            }
            dialog.show()
        }

    }
}