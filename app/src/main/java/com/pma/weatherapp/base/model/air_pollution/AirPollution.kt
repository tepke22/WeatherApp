package com.pma.weatherapp.base.model.air_pollution

data class AirPollution(
    val coord: Coord,
    val list: List<AirPollutionItem>
)