package com.pma.weatherapp.base.data.weather_api

import com.pma.weatherapp.base.functional.Either
import com.pma.weatherapp.base.model.geocoding.Geocoding
import retrofit2.Call
import retrofit2.awaitResponse

class GeocodingDataSource(private val apiService: GeocodingApiService) {

    companion object {
        private const val appid = "abbe04091b4ea9d5b1ca929c5cfc9258"
    }

    suspend fun getCoordinatesByCityName(cityName: String): Either<Geocoding> {
        return handleCall(apiService.getCoordinatesByCityName(cityName, appid))
    }

    suspend fun getCityNameByCordinates(lat: Double, lon: Double): Either<Geocoding> {
        return handleCall(apiService.getCityNameByCoordinates(lat, lon, appid))
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