package com.pma.weatherapp.base.data

import com.pma.weatherapp.base.data.airpollution_api.AirPollutionApiService
import com.pma.weatherapp.base.data.weather_api.GeocodingApiService
import com.pma.weatherapp.base.data.weather_api.WeatherApiBuilder
import com.pma.weatherapp.base.data.weather_api.WeatherApiService

object ApiServiceProvider {
    val weatherApiService = WeatherApiBuilder.retrofit.create(WeatherApiService::class.java)
    val airPollutionApiService =
        WeatherApiBuilder.retrofit.create(AirPollutionApiService::class.java)
    val geocodingApiService = WeatherApiBuilder.retrofit.create(GeocodingApiService::class.java)
}