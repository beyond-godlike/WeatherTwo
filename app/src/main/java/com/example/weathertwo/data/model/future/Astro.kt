package com.example.weathertwo.data.model.future

data class Astro(
    var sunrise: String? = null,
    var sunset: String? = null,
    var moonrise: String? = null,
    var moonset: String? = null,
    var moon_phase: String? = null,
    var moon_illumination: Double? = null,
)