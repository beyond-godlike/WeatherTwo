package com.example.weathertwo.data.di

import com.example.weathertwo.data.api.RetrofitFactory
import com.example.weathertwo.data.api.WeatherApi
import com.example.weathertwo.domain.usecase.GetCurrentWeatherUseCase
import com.example.weathertwo.domain.usecase.GetFutureWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideApi(): WeatherApi = RetrofitFactory.api()

    @Provides
    @Singleton
    fun provideGetCurrentWeatherUseCase(api: WeatherApi) = GetCurrentWeatherUseCase(api)

    @Singleton
    @Provides
    fun provideGetFutureWeatherUseCase(api: WeatherApi) = GetFutureWeatherUseCase(api)
}