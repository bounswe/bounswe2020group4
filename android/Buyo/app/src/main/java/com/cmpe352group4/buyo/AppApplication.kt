package com.cmpe352group4.buyo

import android.app.Activity
import android.app.Application
import android.os.Build
import com.cmpe352group4.buyo.dependencyinjection.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import java.util.*
import javax.inject.Inject

class AppApplication: Application(), HasActivityInjector {
    companion object {
    }
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    // var dialogManager: IDialogManager? = null

    override fun onCreate() {
        super.onCreate()
        val locale = Locale("en")
        Locale.setDefault(locale)

        val resources = resources
        val configuration = resources.configuration
        configuration.setLocale(locale)

        if (Build.VERSION.SDK_INT >= 17) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
            resources.updateConfiguration(configuration, resources.getDisplayMetrics())
        }
        AppInjector.init(this)
        // dialogManager = DialogManager()


    }

    override fun activityInjector() = dispatchingAndroidInjector
}