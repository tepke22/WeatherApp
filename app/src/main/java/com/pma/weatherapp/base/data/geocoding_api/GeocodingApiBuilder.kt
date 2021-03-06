package com.pma.weatherapp.base.data.geocoding_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GeocodingApiBuilder {

    private const val BASE_URL = "http://api.openweathermap.org/geo/1.0/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}