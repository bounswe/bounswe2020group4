package com.cmpe352group4.buyo.dependencyinjection

import android.content.Context
import android.content.SharedPreferences
import com.cmpe352group4.buyo.datamanager.shared_pref.ISharedPref
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class SharedPrefModule {
    companion object {
        private const val SHARED_PREFERENCES = "shared_preferences"
    }

    @Named(SHARED_PREFERENCES)
    @Provides
    fun provideBaseUrl(): String = "buyo"

    @Singleton
    @Provides
    fun provideSharedPrefenreces(@ApplicationContext context: Context,
                                 @Named(SHARED_PREFERENCES) sharedPref: String
    ): SharedPreferences {
        return context.getSharedPreferences(sharedPref, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPref(sharedPref: SharedPref): ISharedPref {
        return sharedPref
    }
}