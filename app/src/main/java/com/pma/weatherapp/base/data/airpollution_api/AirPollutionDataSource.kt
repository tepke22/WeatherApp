package com.pma.weatherapp.base.data.airpollution_api

import com.pma.weatherapp.base.functional.Either
import com.pma.weatherapp.base.model.air_pollution.AirPollution
import retrofit2.Call
import retrofit2.awaitResponse

class AirPollutionDataSource(private val apiService: AirPollutionApiService) {

    companion object {
        private const val appid = "abbe04091b4ea9d5b1ca929c5cfc9258"
//        private const val slat = 43.900059
//        private const val slon = 20.391127
    }

    suspend fun getCurrentAirPollution(lat: Double, lon: Double): Either<AirPollution> {
        return handleCall(apiService.getCurrentAirPollution(lat, lon, appid))
    }

    private suspend fun <T> handleCall(call: Call<T>): Either<T> {
        val response = call.awaitResponse()

        return if (response.isSuccessful) {
            Either.Success(response.body()!!)
        } else {
            Either.Error(Exception(response.message()))
        }
    }
}