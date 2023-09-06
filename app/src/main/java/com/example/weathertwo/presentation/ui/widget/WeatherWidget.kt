package com.example.weathertwo.presentation.ui.widget



import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.layout.Column
import androidx.glance.layout.width
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.work.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.appwidget.cornerRadius
import androidx.glance.layout.Spacer
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.weathertwo.R

class WeatherWidget() : GlanceAppWidget() {

    override val stateDefinition = PreferencesGlanceStateDefinition


    @Composable
    override fun Content() {
        val context = LocalContext.current

        val state = currentState<Preferences>()

        val worker = OneTimeWorkRequestBuilder<WeatherWidgetWorker>().build()
        WorkManager.getInstance(context).enqueue(worker)


        val location = state[stringPreferencesKey("location_key")] ?: ""
        val img = state[stringPreferencesKey("img_key")] ?: ""
        val temp = state[stringPreferencesKey("temp_key")] ?: ""
        val windKph = state[stringPreferencesKey("wind_kph")] ?: ""
        val humidity = state[stringPreferencesKey("humidity_key")] ?: ""

        val weatherState = WeatherState(
            location = location,
            img = img,
            temp = temp,
            windKph = windKph,
            humidity = humidity
        )

        Column(
            modifier = GlanceModifier
                .width(180.dp).height(180.dp)
                .background(Color(0x1D2A6699))
                .cornerRadius(16.dp)
                //.clickable(actionStartActivity(activity = MainActivity::class.java))
                .padding(8.dp)
        ) {
            Text(
                weatherState.location,
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                ),
            )
            Spacer(modifier = GlanceModifier.height(20.dp))

            /*

            Image(
                provider = ImageProvider(R.drawable.weather),
                contentDescription = context.getString(R.string.widget_image),
                contentScale = ContentScale.FillBounds,
                modifier = GlanceModifier.size(50.dp),
            )

             */
            Text(
                weatherState.temp + " °C",
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
            )
            Text(
                context.getString(R.string.wind) + weatherState.windKph,
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
            )

            Text(
                context.getString(R.string.humidity) + weatherState.humidity,
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
            )

            //Button(text = "Update", onClick = actionRunCallback<WidgetRefreshAction>())
        }
    }
}
