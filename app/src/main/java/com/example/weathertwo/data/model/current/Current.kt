package com.example.weathertwo.data.model.current

data class Current(
    var last_updated_epoch: Double? = null,
    val last_updated: String? = null,
    val temp_c: Double? = null,
    val temp_f: Double? = null,
    val is_day: Double? = null,
    val condition: Condition? = null,
    val wind_mph: Double? = null,
    val wind_kph: Double? = null,
    val wind_degree: Double? = null,
    val wind_dir: String? = null,
    val pressure_mb: Double? = null,
    val pressure_in: Double? = null,
    val precip_mm: Double? = null,
    val precip_in: Double? = null,
    val humidity: Double? = null,
    val cloud: Double? = null,
    val feelslike_c: Double? = null,
    val feelslike_f: Double? = null,
    val vis_km: Double? = null,
    val vis_miles: Double? = null,
    val uv: Double? = null,
    val gust_mph: Double? = null,
    val gust_kph: Double? = null,
)