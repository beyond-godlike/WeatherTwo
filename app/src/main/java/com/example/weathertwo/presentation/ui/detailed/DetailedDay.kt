package com.example.weathertwo.presentation.ui.detailed

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathertwo.R
import com.example.weathertwo.data.model.future.Hour
import com.example.weathertwo.presentation.ui.common.Date
import com.example.weathertwo.presentation.ui.common.IconWeather
import com.example.weathertwo.presentation.ui.common.MaxTemp
import com.example.weathertwo.presentation.ui.common.countRGB
import com.example.weathertwo.presentation.ui.common.countRGBStroke


@Composable
fun DetailedDay(
    ind: Int,
    viewModel: DetailedViewModel = viewModel()
) {
    when (val state = viewModel.state.value) {
        is DetailedViewModel.State.START -> {

        }
        is DetailedViewModel.State.LOADING -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }
        is DetailedViewModel.State.SUCCESS -> {
            // для дня грузим с вью модели почасовой прогноз
            val list = state.weather.responseFuture.forecast?.forecastday!![ind].hour!!
            HourlyForecast(list)

        }
        is DetailedViewModel.State.FAILURE -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(text = stringResource(R.string.error), fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun HourlyForecast(list: List<Hour>) {
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(26.dp, 26.dp)) {
        itemsIndexed(items = list) { _, item ->
            DetailedWeatherCard(
                forecast = item,
                Color(countRGB(item.temp_c?.toFloat()!!)),
                Color(countRGBStroke(item.temp_c?.toFloat()!!))
            )
        }
    }
}

@Composable
fun DetailedWeatherCard(forecast: Hour, color: Color, colorStroke: Color) {

    Surface(
        shadowElevation = 9.dp,
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                border = BorderStroke(1.dp, colorStroke),
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = color,
                    shape = RoundedCornerShape(4.dp)
                )
                .border(
                    border = BorderStroke(1.dp, colorStroke),
                    shape = RoundedCornerShape(4.dp)
                ),
            horizontalAlignment = Alignment.Start,
        ) {
            Date(forecast.time!!)
            IconWeather(forecast.condition?.icon.toString())
            MaxTemp(forecast.temp_c.toString())
            Text("uv: " + forecast.uv.toString())
        }
    }
}