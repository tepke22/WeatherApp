package com.pma.weatherapp.base.data

import com.pma.weatherapp.base.data.airpollution_api.AirPollutionApiBuilder
import com.pma.weatherapp.base.data.airpollution_api.AirPollutionApiService
import com.pma.weatherapp.base.data.geocoding_api.GeocodingApiBuilder
import com.pma.weatherapp.base.data.geocoding_api.GeocodingApiService
import com.pma.weatherapp.base.data.weather_api.WeatherApiBuilder
import com.pma.weatherapp.base.data.weather_api.WeatherApiService

object ApiServiceProvider {
    val weatherApiService: WeatherApiService =
        WeatherApiBuilder.retrofit.create(WeatherApiService::class.java)
    val airPollutionApiService: AirPollutionApiService =
        AirPollutionApiBuilder.retrofit.create(AirPollutionApiService::class.java)
    val geocodingApiService: GeocodingApiService = GeocodingApiBuilder.retrofit.create(
        GeocodingApiService::class.java
    )
}