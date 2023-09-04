package com.example.weathertwo.domain.model

import androidx.room.*
import com.example.weathertwo.data.model.Weather
import com.example.weathertwo.data.model.current.CurrentWeatherResponse
import com.example.weathertwo.data.model.current.Location

@Dao
interface WeatherResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: Weather): Long?

    @Update
    suspend fun update(weather: Weather)

    @Delete
    suspend fun delete(weather: Weather)

    @Query("SELECT * from weather")
    fun getWeatherList(): List<Weather>

    @Query("SELECT * FROM weather WHERE location =:location")
    fun getWeather(location: String): Weather
}