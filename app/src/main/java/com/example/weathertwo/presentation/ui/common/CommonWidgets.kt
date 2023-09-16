package com.example.weathertwo.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.weathertwo.R
import com.example.weathertwo.presentation.ui.theme.Black

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
        style = MaterialTheme.typography.titleSmall
    )
}

@Composable
fun MinTemp(minTemp: String) {
    Text(
        text = "$minTemp°C",
        modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 8.dp),
        color = Black,
        style = MaterialTheme.typography.titleSmall
    )
}

@Composable
fun MaxTemp(maxTemp: String) {
    Text(
        text = "$maxTemp°C",
        modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 8.dp),
        color = Black,
        style = MaterialTheme.typography.titleSmall
    )

}