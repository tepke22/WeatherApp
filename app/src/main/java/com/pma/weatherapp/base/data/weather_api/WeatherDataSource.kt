package com.pma.weatherapp.base.data.weather_api

import com.pma.weatherapp.base.functional.Either
import com.pma.weatherapp.base.model.weather.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.awaitResponse

interface IWeatherDataSource{

    suspend fun getCurrentWeather(lat:Double, lon:Double, units: String): Either<WeatherInfo>
    suspend fun getDailyWeather(lat:Double, lon:Double, units: String): Either<WeatherInfo>
    suspend fun getHourlyWeather(lat:Double, lon:Double, units: String): Either<WeatherInfo>
}

class WeatherDataSource(private val apiService: WeatherApiService): IWeatherDataSource {

    companion object {
        private const val appid = "abbe04091b4ea9d5b1ca929c5cfc9258"
//        private const val slat = 55.757512
//        private const val slon = 37.564483
    }

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        units: String
    ): Either<WeatherInfo> {
        return handleCall(apiService.getCurrentWeather(lat, lon, appid, units))
    }

    override suspend fun getDailyWeather(
        lat: Double,
        lon: Double,
        units: String
    ): Either<WeatherInfo> {
        return handleCall(apiService.getDailyWeather(lat, lon, appid, units))
    }

    override suspend fun getHourlyWeather(
        lat: Double,
        lon: Double,
        units: String
    ): Either<WeatherInfo> {
        return handleCall(apiService.getHourlyWeather(lat, lon, appid, units))
    }

    private suspend fun <T> handleCall(call: Call<T>): Either<T> {
        return withContext(Dispatchers.IO) {
            val response = call.execute()

            if (response.isSuccessful) {
                Either.Success(response.body()!!)
            } else {
                Either.Error(Exception(response.message()))
            }
        }
    }
}