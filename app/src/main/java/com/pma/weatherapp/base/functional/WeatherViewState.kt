package com.pma.weatherapp.base.functional

import com.pma.weatherapp.base.model.weather.WeatherInfo

sealed class WeatherViewState{
    object Processing: WeatherViewState()
    data class DataReceived(val weatherInfo: WeatherInfo) : WeatherViewState()
    data class ErrorReceived(val message: String) : WeatherViewState()
}
