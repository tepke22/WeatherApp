package com.pma.weatherapp.base.data.weather_api

import com.pma.weatherapp.base.model.weather.WeatherInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("onecall?exclude=minutely,hourly,daily&units=metric")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): Call<WeatherInfo>

    @GET("onecall?exclude=minutely,hourly,current&units=metric")
    fun getDailyWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): Call<WeatherInfo>

    @GET("onecall?exclude=minutely,daily,current&units=metric")
    fun getHourlyWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): Call<WeatherInfo>
}