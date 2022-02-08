package com.pma.weatherapp.base.model.weather

data class WeatherInfo(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: Current?,
    val minutely: List<Minutely>?,
    val hourly: List<Hourly>?,
    val daily: List<Daily>?,
    val alerts: List<Alert>?
)