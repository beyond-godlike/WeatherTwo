package com.example.weathertwo.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object Day : Screen("day")
    object Month : Screen("month/{city}") {
        fun createRoute(city: String) = "month/$city"
    }
}

@Composable
fun rememberWeatherAppState(
    controller: NavHostController = rememberNavController(),
) = remember(controller) {
    WeatherAppState(controller)
}

class WeatherAppState(val controller: NavHostController) {

    fun navigateToMonth(city: String) {
        controller.navigate(Screen.Month.createRoute(city))
    }

    fun navigateBack() {
        controller.popBackStack()
    }
}