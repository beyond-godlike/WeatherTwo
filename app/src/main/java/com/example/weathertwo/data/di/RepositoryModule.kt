package com.example.weathertwo.data.di

import android.content.Context
import androidx.room.Room
import com.example.weathertwo.domain.model.WeatherResponseDao
import com.example.weathertwo.domain.repository.AppDatabase
import com.example.weathertwo.domain.repository.IWeatherRepository
import com.example.weathertwo.domain.repository.WeatherRepository
import com.example.weathertwo.domain.prefs.AppPrefs
import com.example.weathertwo.domain.prefs.PrefsHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providePrefHelper(appPrefs: AppPrefs): PrefsHelper {
        return appPrefs
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "wDB"
    ).allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideWeatherDao(db: AppDatabase) = db.weatherDao()


    @Provides
    @Singleton
    fun provideRepository(
        weatherDao: WeatherResponseDao,
    ): IWeatherRepository =
        WeatherRepository(weatherDao)

}