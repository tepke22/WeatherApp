package com.pma.weatherapp.base.model.air_pollution

data class Coord(
    val lon: Double,
    val lat: Double
){
    constructor() : this(0.0,0.0)
}