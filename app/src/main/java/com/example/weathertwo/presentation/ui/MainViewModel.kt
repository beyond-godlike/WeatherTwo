package com.example.weathertwo.presentation.ui

import android.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertwo.data.model.Weather
import com.example.weathertwo.domain.prefs.PrefsHelper
import com.example.weathertwo.domain.repository.AppDatabase
import com.example.weathertwo.domain.usecase.GetCurrentWeatherUseCase
import com.example.weathertwo.domain.usecase.GetFutureWeatherUseCase
import com.example.weathertwo.presentation.ui.base.BaseViewModel
import com.example.weathertwo.presentation.ui.base.ViewAction
import com.example.weathertwo.presentation.ui.base.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currentWeatherUseCase: GetCurrentWeatherUseCase,
    private val futureWeatherUseCase: GetFutureWeatherUseCase,
    private val database: AppDatabase,
    private val prefsHelper: PrefsHelper,
) : BaseViewModel<MainViewModel.State, MainViewModel.Action>(
    initialState = State.START
) {
    var city by mutableStateOf("")

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        fetchWeather()
    }

    override fun dispatch(action: Action) {
        when(action) {
            is Action.FetchWeather -> {
                updateState(State.LOADING)
                fetchWeather()
            }
        }
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            try {
                val responseCurrent =
                    currentWeatherUseCase.getCurrentWeather(prefsHelper.getLastCity())
                val responseFuture =
                    futureWeatherUseCase.getFutureWeather(prefsHelper.getLastCity(), 7)

                val response = Weather(prefsHelper.getLastCity(), responseCurrent, responseFuture)

                updateState(State.SUCCESS(response))

            } catch (e: Exception) {
                val response = database.weatherDao().getWeather(prefsHelper.getLastCity())
                if (response.responseFuture.current != null){
                    updateState(State.SUCCESS(response))
                } else {
                    updateState(State.FAILURE("response is null"))
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveCity() {
        prefsHelper.saveLastCity(city)
    }


    private fun fib(n: Int): Int {
        return if (n <= 1) n else fib(n - 1) + fib(n - 2)
    }

    fun countRGBStroke(avg: Float): Int {
        val hsv = FloatArray(3)
        hsv[0] = 359.0f - (200.0f + (fib(avg.toInt()) / 100.0f))
        hsv[1] = abs(avg) / 100
        hsv[2] = 0.8f


        return Color.HSVToColor(hsv)
    }

    fun countRGB(avg: Float): Int {
        val hsv = FloatArray(3)
        hsv[0] = 359.0f - (200.0f + (fib(avg.toInt()) / 100.0f))
        hsv[1] = abs(avg) / 100
        hsv[2] = 1.0f

        return Color.HSVToColor(hsv)
    }

    sealed class State : ViewState {
        object START : State()
        object LOADING : State()
        data class SUCCESS(val weather: Weather) : State()
        data class FAILURE(val message: String) : State()
    }

    sealed class Action : ViewAction {
        //data class FetchCurrentWeather(val city: String) : Action()
        object FetchWeather : Action()
    }
}