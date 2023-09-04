package com.example.weathertwo.domain.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weathertwo.data.model.Weather
import com.example.weathertwo.domain.model.WeatherResponseDao


@Database(
    entities = [Weather::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    WeatherConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherResponseDao
}