package com.example.weathertwo.domain.prefs

interface PrefsHelper {
    fun isFirstRun(): Boolean

    fun setFirstRun(enable: Boolean = false)

    fun saveLastCity(cityName: String)

    fun getLastCity(): String
}