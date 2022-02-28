package com.pma.weatherapp.base.model.air_pollution

data class Coord(
    var lat: Double,
    var lon: Double
){
    constructor() : this(0.0,0.0)
}