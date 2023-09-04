package com.example.weathertwo.domain.repository

import androidx.room.TypeConverter
import com.example.weathertwo.data.model.Weather
import com.example.weathertwo.data.model.current.Current
import com.example.weathertwo.data.model.current.CurrentWeatherResponse
import com.example.weathertwo.data.model.current.Location
import com.example.weathertwo.data.model.future.Forecast
import com.example.weathertwo.data.model.future.FutureWeatherResponse
import com.squareup.moshi.Moshi

class WeatherConverter {

    @TypeConverter
    fun fromWeather(value: Weather): String? =
        Moshi.Builder().build().adapter(Weather::class.java).toJson(value)

    @TypeConverter
    fun toWeather(value: String): Weather? =
        value.let {
            Moshi.Builder().build().adapter(Weather::class.java).fromJson(it)
        }

    @TypeConverter
    fun fromCurrentWeatherResponse(value: CurrentWeatherResponse): String? =
        Moshi.Builder().build().adapter(CurrentWeatherResponse::class.java).toJson(value)

    @TypeConverter
    fun toCurrentWeatherResponse(value: String): CurrentWeatherResponse? =
        value.let {
            Moshi.Builder().build().adapter(CurrentWeatherResponse::class.java).fromJson(it)
        }

    @TypeConverter
    fun fromFutureWeatherResponse(value: FutureWeatherResponse): String? =
        Moshi.Builder().build().adapter(FutureWeatherResponse::class.java).toJson(value)

    @TypeConverter
    fun toFutureWeatherResponse(value: String): FutureWeatherResponse? =
        value.let {
            Moshi.Builder().build().adapter(FutureWeatherResponse::class.java).fromJson(it)
        }
}