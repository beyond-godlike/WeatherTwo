package com.example.weathertwo.data.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {
    private var interceptor = Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("key", "72145f7d201a432c92a180346212112")
            .build()

        val newRequest = chain.request().newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val lg = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(lg)
        .addInterceptor(interceptor)
        .build()

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    fun api(): WeatherApi {
        return retrofit().create(WeatherApi::class.java)
    }
}