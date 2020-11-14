package com.cmpe352group4.buyo.datamanager.network

import com.cmpe352group4.buyo.api.Api
import com.cmpe352group4.buyo.datamanager.network.interceptors.HeaderInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

private const val TIMEOUT_MILLIS = "timeout_millis"
private const val TIMEOUT_UNIT = "timeout_unit"
private const val PRIMARY_URL = "primary_url"
private const val PRIMARY_RETRO = "primary_retrofit"
private const val NETWORK_GSON = "network_gson"

@Module
class NetworkModule {

    @Provides
    @Named(PRIMARY_URL)
    fun providePrimaryURl(): String = "https://buyo/api/"

    @Provides
    @Named(TIMEOUT_MILLIS)
    fun provideTimeOutMillis() = 30000L

    @Provides
    @Named(TIMEOUT_UNIT)
    fun provideTimeOutUnit() = TimeUnit.MILLISECONDS


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        headerInterceptor: HeaderInterceptor,
        httpInterceptor: HttpLoggingInterceptor,
        @Named(TIMEOUT_MILLIS) timeOutMillis: Long,
        @Named(TIMEOUT_UNIT) timeOutUnit: TimeUnit
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(httpInterceptor)
            .readTimeout(timeOutMillis, timeOutUnit)
            .writeTimeout(timeOutMillis, timeOutUnit)
            .connectTimeout(timeOutMillis, timeOutUnit)
            .build()
    }

    @Provides
    @Singleton
    @Named(NETWORK_GSON)
    fun provideGsonForRetrofit(): Gson {
        return Gson().newBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(@Named(NETWORK_GSON) gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideLiveDataCallAdapterFactory(liveDataCallAdapterFactory: LiveDataCallAdapterFactory): CallAdapter.Factory {
        return liveDataCallAdapterFactory
    }

    @Provides
    @Singleton
    @Named(PRIMARY_RETRO)
    fun provideRetrofit(
        @Named(PRIMARY_URL) url: String,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .client(client)
            .build()
    }


    @Provides
    @Singleton
    fun providePrimaryApi(
        @Named(PRIMARY_RETRO) retrofit: Retrofit
    ): Api {
        return retrofit.create(Api::class.java)
    }


}