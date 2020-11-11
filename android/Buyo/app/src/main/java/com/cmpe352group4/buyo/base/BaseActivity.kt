package com.cmpe352group4.buyo.base

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.util.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    protected abstract fun layoutId(): Int

    protected abstract fun initialize()

    protected open fun finalize() {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
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
        initialize()
    }

    override fun onDestroy() {
        super.onDestroy()
        finalize()
    }

    override fun onPause() {
        overridePendingTransition(0, 0)
        super.onPause()
    }

}