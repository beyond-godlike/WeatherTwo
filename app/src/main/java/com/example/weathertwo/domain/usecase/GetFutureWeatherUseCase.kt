package com.example.weathertwo.domain.usecase

import com.example.weathertwo.data.api.WeatherApi
import com.example.weathertwo.data.model.future.FutureWeatherResponse

class GetFutureWeatherUseCase (
    private val api: WeatherApi
) {
    suspend fun getFutureWeather(s: String, days: Int) : FutureWeatherResponse {
        return api.getFutureWeatherAsync(s, days)
    }
}