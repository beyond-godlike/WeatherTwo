package com.example.weathertwo.data.model.current

data class CurrentWeatherResponse(
    var location: Location,
    var current: Current? = null
)