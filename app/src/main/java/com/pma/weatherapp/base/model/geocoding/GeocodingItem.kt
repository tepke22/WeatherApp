package com.pma.weatherapp.base.model.geocoding

data class GeocodingItem(
    val name: String,
    val local_names: LocalNames,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?
)