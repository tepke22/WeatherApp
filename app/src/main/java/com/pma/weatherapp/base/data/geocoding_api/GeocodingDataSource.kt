package com.pma.weatherapp.base.data.geocoding_api

import com.pma.weatherapp.base.functional.Either
import com.pma.weatherapp.base.model.geocoding.Geocoding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.awaitResponse

interface IGeocodingDataSource{

    suspend fun getCoordinatesByCityName(cityName:String, limit:Int): Either<Geocoding>
    suspend fun getCityNameByCordinates(lat:Double, lon:Double, limit:Int): Either<Geocoding>
}

class GeocodingDataSource(private val apiService: GeocodingApiService): IGeocodingDataSource {

    companion object {
        private const val appid = "abbe04091b4ea9d5b1ca929c5cfc9258"
    }

    override suspend fun getCoordinatesByCityName(cityName: String, limit: Int): Either<Geocoding> {
        return handleCall(apiService.getCoordinatesByCityName(cityName, appid, limit))
    }

    override suspend fun getCityNameByCordinates(
        lat: Double,
        lon: Double,
        limit: Int,
    ): Either<Geocoding> {
        return handleCall(apiService.getCityNameByCoordinates(lat, lon, appid, limit))
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