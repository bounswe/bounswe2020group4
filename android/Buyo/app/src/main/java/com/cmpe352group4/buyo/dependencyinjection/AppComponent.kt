package com.cmpe352group4.buyo.dependencyinjection

import android.app.Application
import com.cmpe352group4.buyo.AppApplication
import com.cmpe352group4.buyo.datamanager.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class,
        NetworkModule::class,
        SharedPrefModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: AppApplication)
}