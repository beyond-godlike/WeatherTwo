package com.example.weathertwo.domain.repository

import com.example.weathertwo.data.model.Weather
import com.example.weathertwo.data.model.current.CurrentWeatherResponse
import com.example.weathertwo.data.model.current.Location
import com.example.weathertwo.data.model.future.FutureWeatherResponse

interface IWeatherRepository {
    suspend fun getWeather(location: String) : Weather
    suspend fun insertWeather(weather: Weather) : Long?
}