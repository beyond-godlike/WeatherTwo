package com.example.weathertwo.presentation.ui.common

import android.graphics.Color
import kotlin.math.abs

private fun fib(n: Int): Int {
    return if (n <= 1) n else fib(n - 1) + fib(n - 2)
}

fun countRGBStroke(avg: Float): Int {
    val hsv = FloatArray(3)
    hsv[0] = 359.0f - (200.0f + (fib(avg.toInt()) / 100.0f))
    hsv[1] = abs(avg) / 100
    hsv[2] = 0.8f


    return Color.HSVToColor(hsv)
}

fun countRGB(avg: Float): Int {
    val hsv = FloatArray(3)
    hsv[0] = 359.0f - (200.0f + (fib(avg.toInt()) / 100.0f))
    hsv[1] = abs(avg) / 100
    hsv[2] = 1.0f

    return Color.HSVToColor(hsv)
}