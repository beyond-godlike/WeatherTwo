package com.example.weathertwo.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import com.example.weathertwo.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherApp() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val tabs =
        listOf(context.getString(R.string.current_tab), context.getString(R.string.forecast_tab))

    val pagerState = rememberPagerState { tabs.size }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPos ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPos[pagerState.currentPage])
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

            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState,
            ) { page ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (page) {
                        0 -> DayScreen(viewModel = hiltViewModel())
                        1 -> MonthScreen(viewModel = hiltViewModel())
                    }
                }
            }
        }
    }
}
