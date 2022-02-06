package com.pma.weatherapp.base.model.air_pollution

data class AirPollutionItem(
    val main: Main,
    val components: Components,
    val dt: Int
)