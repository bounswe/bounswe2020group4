package com.cmpe352group4.buyo.dependencyinjection

import com.cmpe352group4.buyo.FirstFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBuilderModule {

    // AFTER CREATING NEW FRAGMENT YOU NEED TO ADD AN INJECTOR FUNCTION LIKE THE OTHERS
    @ContributesAndroidInjector
    abstract fun contributeFirstFragment(): FirstFragment


}