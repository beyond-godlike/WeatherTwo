@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.weathertwo.presentation.ui.day

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.weathertwo.R
import com.example.weathertwo.data.model.current.CurrentWeatherResponse
import com.example.weathertwo.presentation.ui.MainViewModel
import com.example.weathertwo.presentation.ui.theme.Black
import com.example.weathertwo.presentation.ui.theme.ColorPrimaryDark
import com.example.weathertwo.presentation.ui.theme.Grey

@Composable
fun DayScreen(
    viewModel: MainViewModel = viewModel(),
    //onBackPressed: () -> Unit
    //navigateToMonth: (String) -> Unit,
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
                Text(text = state.message, fontSize = 16.sp)
            }
            viewModel.city = "London"
            viewModel.saveCity()
        }
        is MainViewModel.State.SUCCESS -> {
            Day(state.weather.responseCurrent, viewModel)
        }
    }
}

@Composable
fun Day(weather: CurrentWeatherResponse, viewModel: MainViewModel) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            LabelCity()
            EditTextCity(viewModel)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            WeatherImage("https:" + weather.current!!.condition!!.icon!!)
            Spacer(modifier = Modifier.height(16.dp))
            LabelCityName(weather.location.name ?: "London")
            Spacer(modifier = Modifier.height(16.dp))
            LabelTemp(weather.current?.feelslike_c.toString())
            Spacer(modifier = Modifier.height(16.dp))
            LabelWeather(weather.current?.condition?.text.toString())
        }
    }
}

@Composable
fun LabelCity() {
    Text(
        text = "City",
        modifier = Modifier.padding(20.dp, 20.dp, 8.dp, 8.dp),
        color = Black,
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
fun EditTextCity(viewModel: MainViewModel) {
    OutlinedTextField(
        modifier = Modifier
            .padding(8.dp, 20.dp, 8.dp, 8.dp),
        value = viewModel.city,
        onValueChange = {
            viewModel.city = it
        },
        shape = RoundedCornerShape(4.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                viewModel.saveCity()
                viewModel.dispatch(MainViewModel.Action.FetchWeather)
            }
        )
    )

}

@Composable
fun ButtonOk(viewModel: MainViewModel) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = {
                viewModel.saveCity()
                viewModel.dispatch(MainViewModel.Action.FetchWeather)
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp, 20.dp),
            elevation = ButtonDefaults.buttonElevation(10.dp)
        ) {
            Text(text = "OK")
        }
    }
}

@Composable
fun WeatherImage(imgUrl: String) {
    val painter = rememberImagePainter(
        data = imgUrl,
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
fun LabelCityName(city: String) {
    Text(
        text = city,
        modifier = Modifier.padding(5.dp),
        color = Black,
        style = MaterialTheme.typography.headlineLarge
    )

}

@Composable
fun LabelTemp(temp: String) {
    Text(
        text = "$temp°C",
        color = ColorPrimaryDark,
        modifier = Modifier.padding(5.dp),
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
fun LabelWeather(weather: String) {
    Text(
        text = weather,
        color = Grey,
        style = MaterialTheme.typography.headlineSmall
    )

}