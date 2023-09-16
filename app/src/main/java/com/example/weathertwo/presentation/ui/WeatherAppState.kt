package com.example.weathertwo.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weathertwo.presentation.ui.detailed.DetailedDay
import com.example.weathertwo.presentation.ui.detailed.DetailedViewModel

sealed class Screen(val route: String) {
    object WeatherPager : Screen("pager")
    object DetailedDay : Screen("detailed/{ind}") {
        fun createRoute(ind: Int) = "detailed/$ind"
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.WeatherPager.route) {
        composable(route = Screen.WeatherPager.route) {
            WeatherApp(navController = navController)
        }
        composable(
            route = Screen.DetailedDay.route,
            arguments = listOf(
                navArgument("ind") {
                    type = NavType.IntType
                }
            )
        ) { entry->
            DetailedDay(ind = entry.arguments?.getInt("ind")!!, hiltViewModel())
        }
    }
}