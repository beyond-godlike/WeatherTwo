package com.example.weathertwo.data.api

import com.example.weathertwo.data.model.current.CurrentWeatherResponse
import com.example.weathertwo.data.model.future.FutureWeatherResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current.json")
    suspend fun getCurrentWeatherAsync(
        @Query("q") q:String
    ): CurrentWeatherResponse

    @GET("forecast.json")
    suspend fun getFutureWeatherAsync(
        @Query("q") location: String,
        @Query("days") days: Int,
    ): FutureWeatherResponse

}