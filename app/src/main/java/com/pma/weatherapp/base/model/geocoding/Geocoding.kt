package com.pma.weatherapp.base.model.geocoding

class Geocoding : ArrayList<GeocodingItem>() {
    override fun toString(): String {
        return "${this[0].name}, ${this[0].country}"
    }
}