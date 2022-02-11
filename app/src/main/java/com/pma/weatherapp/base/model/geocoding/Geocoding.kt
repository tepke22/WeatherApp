package com.pma.weatherapp.base.model.geocoding

class Geocoding : ArrayList<GeocodingItem>() {
    override fun toString(): String {
        return "${this.get(0).name}, ${this.get(0).country}"
    }
}