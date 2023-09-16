package com.example.weathertwo.presentation.ui.detailed

import androidx.lifecycle.viewModelScope
import com.example.weathertwo.data.model.Weather
import com.example.weathertwo.domain.prefs.PrefsHelper
import com.example.weathertwo.domain.repository.AppDatabase
import com.example.weathertwo.presentation.ui.base.BaseViewModel
import com.example.weathertwo.presentation.ui.base.ViewAction
import com.example.weathertwo.presentation.ui.base.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailedViewModel @Inject constructor(
    private val database: AppDatabase,
    private val prefs: PrefsHelper
) : BaseViewModel<DetailedViewModel.State, DetailedViewModel.Action>(
    initialState = State.START
){
    init {
        getWeather()
    }


    private fun getWeather() {
        viewModelScope.launch {
            val response = database.weatherDao().getWeather(prefs.getLastCity())
            if (response.responseFuture.current != null){
                updateState(State.SUCCESS(response))
            } else {
                updateState(State.FAILURE("no data in db"))
            }
        }
    }
    sealed class State : ViewState {
        object START : State()
        object LOADING : State()
        data class SUCCESS(val weather: Weather) : State()
        data class FAILURE(val message: String) : State()
    }

    sealed class Action : ViewAction {
        //data class FetchCurrentWeather(val city: String) : Action()
        object GetWeather : Action()
    }

    override fun dispatch(action: Action) {
        when(action) {
            is Action.GetWeather -> {
                updateState(State.LOADING)
                getWeather()
            }
        }
    }
}