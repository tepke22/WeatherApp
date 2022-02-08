package com.pma.weatherapp.base.data

import com.pma.weatherapp.base.data.weather_api.WeatherApiBuilder
import com.pma.weatherapp.base.data.weather_api.WeatherApiService

object ApiServiceProvider {
    val weatherApiService = WeatherApiBuilder.retrofit.create(WeatherApiService::class.java)
}