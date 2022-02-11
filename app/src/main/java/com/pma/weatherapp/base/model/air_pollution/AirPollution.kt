package com.pma.weatherapp.base.model.air_pollution

data class AirPollution(
    val coord: Coord,
    val list: List<AirPollutionItem>
) {
    override fun toString(): String {
        val airpoll = list.get(0)
        return "Index: " + airpoll.main.aqi + " | CO: " + airpoll.components.co + " | NO: " + airpoll.components.no + " | O3: " + airpoll.components.o3
    }
}