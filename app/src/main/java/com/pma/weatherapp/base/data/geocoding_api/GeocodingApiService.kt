package com.pma.weatherapp.base.data.weather_api

import com.pma.weatherapp.base.model.geocoding.Geocoding
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {

    @GET("direct")
    fun getCoordinatesByCityName(
        @Query("q") name: String,
        @Query("appid") appid: String,
        @Query("limit") limit: Int = 5
    ): Call<Geocoding>

    @GET("reverse?limit=5")
    fun getCityNameByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("limit") limit: Int = 5
    ): Call<Geocoding>

}