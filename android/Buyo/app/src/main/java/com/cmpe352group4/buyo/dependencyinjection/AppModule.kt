package com.cmpe352group4.buyo.dependencyinjection

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module(includes = [ViewModelModule::class])
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun bindContext(application: Application): Context
}