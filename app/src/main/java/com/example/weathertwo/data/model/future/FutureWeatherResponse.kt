package com.example.weathertwo.data.model.future

import com.example.weathertwo.data.model.current.Current

import com.example.weathertwo.data.model.current.Location

data class FutureWeatherResponse(
    //@PrimaryKey var id: Int? = null,
    val location: Location,
    var current : Current? = null,
    var forecast : Forecast? = null
)