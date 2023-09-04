package com.example.weathertwo.domain.usecase

import com.example.weathertwo.data.api.WeatherApi
import com.example.weathertwo.data.model.current.CurrentWeatherResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentWeatherUseCase (
    private val api: WeatherApi
) {
    suspend fun getCurrentWeather(s: String) : CurrentWeatherResponse {
        return api.getCurrentWeatherAsync(s)
    }
}