package com.example.weathertwo.presentation.ui.base

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

interface ViewState
interface ViewAction

abstract class BaseViewModel<S : ViewState, A : ViewAction>(initialState: S) : ViewModel() {
    private val _state = mutableStateOf(initialState)
    val state : State<S> = _state

    // pass
    abstract fun dispatch(action: A)

    fun updateState(newState: S) {
        if(newState != _state.value){
            _state.value = newState
        }
    }
}