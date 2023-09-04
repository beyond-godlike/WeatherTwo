package com.example.weathertwo.domain.repository

import com.example.weathertwo.data.model.Weather
import com.example.weathertwo.data.model.current.CurrentWeatherResponse
import com.example.weathertwo.data.model.current.Location
import com.example.weathertwo.data.model.future.FutureWeatherResponse
import com.example.weathertwo.domain.model.WeatherResponseDao
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherDao: WeatherResponseDao,
) : IWeatherRepository {

    override suspend fun getWeather(location: String): Weather {
        return weatherDao.getWeather(location)
    }

    override suspend fun insertWeather(weather: Weather): Long? {
        return weatherDao.insert(weather)
    }
}