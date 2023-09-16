package com.example.weathertwo

import com.example.weathertwo.presentation.BaseApp
import dagger.hilt.android.testing.CustomTestApplication

@CustomTestApplication(BaseApp::class)
interface HiltTestApplication