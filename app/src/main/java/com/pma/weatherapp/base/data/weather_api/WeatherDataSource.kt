package com.pma.weatherapp.base.data.weather_api

import com.pma.weatherapp.base.functional.Either
import com.pma.weatherapp.base.model.weather.WeatherInfo
import retrofit2.Call
import retrofit2.awaitResponse

class WeatherDataSource(private val apiService: WeatherApiService) {

    companion object {
        private const val appid = "abbe04091b4ea9d5b1ca929c5cfc9258"
        private const val lat = 43.900059
        private const val lon = 20.391127
    }

    suspend fun getCurrentWeather(): Either<WeatherInfo> {
        return handleCall(apiService.getCurrentWeather(lat, lon, appid))
    }

    suspend fun getDailyWeather(): Either<WeatherInfo> {
        return handleCall(apiService.getDailyWeather(lat, lon, appid))
    }

    suspend fun getHourlyWeather(): Either<WeatherInfo> {
        return handleCall(apiService.getHourlyWeather(lat, lon, appid))
    }

    suspend fun <T> handleCall(call: Call<T>): Either<T> {
        val response = call.awaitResponse()

        return if (response.isSuccessful) {
            Either.Success(response.body()!!)
        } else {
            Either.Error(Exception(response.message()))
        }
    }
}