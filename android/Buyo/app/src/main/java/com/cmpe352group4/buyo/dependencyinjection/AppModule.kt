package com.cmpe352group4.buyo.dependencyinjection

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

// ViewModelModule::class it will be added to includes in @Module afte creating viewmodel and its module
@Module(includes = [])
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun bindContext(application: Application): Context
}