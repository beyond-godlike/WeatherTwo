package com.example.weathertwo.presentation.ui.widget

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weathertwo.data.model.Weather
import com.example.weathertwo.domain.di.DefaultDispatcher
import com.example.weathertwo.domain.prefs.PrefsHelper
import com.example.weathertwo.domain.repository.WeatherRepository
import com.example.weathertwo.domain.usecase.GetCurrentWeatherUseCase
import com.example.weathertwo.domain.usecase.GetFutureWeatherUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Named

@HiltWorker
class WeatherWidgetWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val currentUseCase: GetCurrentWeatherUseCase,
    private val futureUseCase: GetFutureWeatherUseCase,
    private val shared: PrefsHelper,
    @DefaultDispatcher
    val dispatcher: CoroutineDispatcher,
    private val repository: WeatherRepository,
) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result {
        getWeather()

        val weather = shared.getLastCity().let { repository.getWeather(it) }
        
        return try {
            updateWidget(
                weather.responseCurrent.location.name,
                weather.responseCurrent.current?.condition?.icon.toString(),
                weather.responseCurrent.current?.feelslike_c.toString(),
                weather.responseCurrent.current?.wind_kph.toString(),
                weather.responseCurrent.current?.humidity.toString(),
                context
            )
            
            Result.success()
        } catch(e: Exception) {
            Result.retry()
        }
    }

    suspend fun updateWidget(
        location: String,
        img: String,
        temp: String,
        windKph: String,
        humidity: String,
        context: Context
    ) {
        GlanceAppWidgetManager(context).getGlanceIds(WeatherWidget::class.java).forEach { glanceId ->
            updateAppWidgetState(context, glanceId) { prefs ->
                prefs[stringPreferencesKey("location_key")] = location
                prefs[stringPreferencesKey("img_key")] = img
                prefs[stringPreferencesKey("temp_key")] = temp
                prefs[stringPreferencesKey("wind_kph")] = windKph
                prefs[stringPreferencesKey("humidity_key")] = humidity
            }
        }
       WeatherWidget().updateAll(context)
    }

    private fun getWeather() {
        CoroutineScope(dispatcher).launch {
            try {
                val cresponse = currentUseCase.getCurrentWeather(shared.getLastCity())
                val fresponse = futureUseCase.getFutureWeather(shared.getLastCity(), 7)
                val response = Weather(shared.getLastCity(), cresponse, fresponse)

                if (fresponse.current != null || cresponse.current != null) {
                    repository.insertWeather(response)
                }
            } catch (e: Exception) {
            }
        }
    }

}