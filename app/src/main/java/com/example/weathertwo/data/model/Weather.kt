package com.example.weathertwo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weathertwo.data.model.current.CurrentWeatherResponse
import com.example.weathertwo.data.model.current.Location
import com.example.weathertwo.data.model.future.FutureWeatherResponse

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey val location: String,
    var responseCurrent: CurrentWeatherResponse,
    var responseFuture: FutureWeatherResponse
)