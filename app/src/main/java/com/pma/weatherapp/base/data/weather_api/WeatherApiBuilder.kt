package com.pma.weatherapp.base.data.weather_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherApiBuilder {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}