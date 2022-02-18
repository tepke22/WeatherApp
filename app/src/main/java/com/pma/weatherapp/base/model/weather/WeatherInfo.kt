package com.pma.weatherapp.base.model.weather

import com.google.gson.annotations.SerializedName

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
){
    constructor() : this(0.0,0.0,"",0, Current(),listOf(),listOf(),listOf(),listOf())
}