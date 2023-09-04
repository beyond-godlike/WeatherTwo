package com.example.weathertwo.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weathertwo.presentation.ui.day.DayScreen
import com.example.weathertwo.presentation.ui.month.MonthScreen
import com.example.weathertwo.presentation.ui.theme.ColorAccent
import com.example.weathertwo.presentation.ui.theme.ColorPrimaryDark
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.ui.platform.LocalContext
import com.example.weathertwo.R

import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun WeatherApp() {

    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState()
        val tabs = listOf(context.getString(R.string.current_tab), context.getString(R.string.forecast_tab))
        val state by remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope()


        //HorizontalPager
        //VerticalPager

        //https://developer.android.com/jetpack/androidx/releases/compose-foundation#1.4.0-alpha03
        // TAB
        Column {
            TabRow(selectedTabIndex = state,
                indicator = {
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, it)
                    )
                },
            ) {
                tabs.forEachIndexed { index, text ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = text,
                                color = if (pagerState.currentPage == index) ColorPrimaryDark else ColorAccent
                            )
                        }
                    )
                }
            }

            // PAGER
            HorizontalPager(
                count = tabs.size,
                state = pagerState,
            ) { index ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (index) {
                        0 -> DayScreen(viewModel = hiltViewModel())
                        1 -> MonthScreen(viewModel = hiltViewModel())
                    }
                }
            }
        }
    }
}