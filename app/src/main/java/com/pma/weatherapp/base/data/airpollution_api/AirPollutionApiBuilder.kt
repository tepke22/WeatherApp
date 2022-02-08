package com.pma.weatherapp.base.data.airpollution_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AirPollutionApiBuilder {

    private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}