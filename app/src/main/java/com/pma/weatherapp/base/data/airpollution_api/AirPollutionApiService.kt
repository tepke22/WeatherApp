package com.pma.weatherapp.base.data.airpollution_api

import com.pma.weatherapp.base.model.air_pollution.AirPollution
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AirPollutionApiService {

    @GET("air_pollution")
    fun getCurrentAirPollution(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): Call<AirPollution>

}