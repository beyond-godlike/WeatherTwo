package com.example.weathertwo.presentation.ui.month

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathertwo.data.model.future.Forecastday
import com.example.weathertwo.presentation.ui.MainViewModel
import com.example.weathertwo.R
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.weathertwo.presentation.ui.Screen
import com.example.weathertwo.presentation.ui.common.Date
import com.example.weathertwo.presentation.ui.common.IconWeather
import com.example.weathertwo.presentation.ui.common.MaxTemp
import com.example.weathertwo.presentation.ui.common.MinTemp
import com.example.weathertwo.presentation.ui.common.countRGB
import com.example.weathertwo.presentation.ui.common.countRGBStroke

@Composable
fun MonthScreen(
    viewModel: MainViewModel = viewModel(),
    navController: NavController,
    //onBackPressed: () -> Unit
) {
    when (val state = viewModel.state.value) {
        is MainViewModel.State.START -> {

        }
        is MainViewModel.State.LOADING -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }
        is MainViewModel.State.FAILURE -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(text = stringResource(R.string.error), fontSize = 16.sp)
            }
        }
        is MainViewModel.State.SUCCESS -> {
            val weather = state.weather.responseFuture
            WeatherList(forecastList = weather.forecast?.forecastday!!, navController)
        }

        else -> {}
    }
}

@Composable
fun WeatherList(
    forecastList: List<Forecastday>,
    navController: NavController
) {

    LazyRow(
        modifier = Modifier
            .padding(0.dp, 26.dp, 0.dp, 0.dp)
    ) {
        itemsIndexed(items = forecastList) {it, item ->
            WeatherCard(
                forecast = item,
                Color(countRGB(item.day?.avgtemp_c?.toFloat()!!)),
                Color(countRGBStroke(item.day?.avgtemp_c?.toFloat()!!)),
                onItemClick = { _ ->
                    navController.navigate(Screen.DetailedDay.createRoute(it))
                },
            )

        }
    }
}

@Composable
fun WeatherCard(
    forecast: Forecastday,
    color: Color,
    colorStroke: Color,
    onItemClick:(date: String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                border = BorderStroke(1.dp, colorStroke),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable {
                onItemClick(forecast.date.toString())
            },
        horizontalAlignment = Alignment.Start
    ) {
        Date(forecast.date!!)
        IconWeather(forecast.day?.condition?.icon.toString())
        MaxTemp(forecast.day?.maxtemp_c.toString())
        MinTemp(forecast.day?.mintemp_c.toString())
    }
}


