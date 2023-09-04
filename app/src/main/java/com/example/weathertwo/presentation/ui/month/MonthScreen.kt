package com.example.weathertwo.presentation.ui.month

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.weathertwo.data.model.future.Forecastday
import com.example.weathertwo.presentation.ui.MainViewModel
import com.example.weathertwo.presentation.ui.theme.Black
import com.example.weathertwo.R
import androidx.compose.ui.platform.LocalContext

@Composable
fun MonthScreen(
    viewModel: MainViewModel = viewModel(),
    //onBackPressed: () -> Unit
) {
    val context = LocalContext.current

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
                Text(text = context.getString(R.string.error), fontSize = 16.sp)
            }
        }
        is MainViewModel.State.SUCCESS -> {
            val weather = state.weather.responseFuture
            WeatherList(forecastList = weather.forecast?.forecastday!!, viewModel)
        }
    }
}

@Composable
fun WeatherList(forecastList: List<Forecastday>, viewModel: MainViewModel) {
    LazyRow(modifier = Modifier.padding(0.dp, 26.dp, 0.dp, 0.dp)) {
        itemsIndexed(items = forecastList) { _, item ->
            WeatherCard(
                forecast = item,
                Color(viewModel.countRGB(item.day?.avgtemp_c?.toFloat()!!)),
                Color(viewModel.countRGBStroke(item.day?.avgtemp_c?.toFloat()!!))
            )

        }
    }
}

@Composable
fun WeatherCard(forecast: Forecastday, color: Color, colorStroke: Color) {
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
            ),
        horizontalAlignment = Alignment.Start
    ) {
        Date(forecast.date!!)
        IconWeather(forecast.day?.condition?.icon.toString())
        MaxTemp(forecast.day?.maxtemp_c.toString())
        MinTemp(forecast.day?.mintemp_c.toString())
    }
}

@Composable
fun IconWeather(imgUrl: String) {
    val painter = rememberImagePainter(
        data = "https:$imgUrl",
        builder = {
            placeholder(R.drawable.weather)
            crossfade(true)
        }
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(40.dp)
    )
}

@Composable
fun Date(date: String) {
    Text(
        text = date,
        modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 8.dp),
        color = Black,
        style = MaterialTheme.typography.button
    )
}

@Composable
fun MinTemp(minTemp: String) {
    Text(
        text = "$minTemp°C",
        modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 8.dp),
        color = Black,
        style = MaterialTheme.typography.button
    )
}

@Composable
fun MaxTemp(maxTemp: String) {
    Text(
        text = "$maxTemp°C",
        modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 8.dp),
        color = Black,
        style = MaterialTheme.typography.button
    )

}