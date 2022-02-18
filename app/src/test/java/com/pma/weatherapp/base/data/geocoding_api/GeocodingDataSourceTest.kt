package com.pma.weatherapp.base.data.geocoding_api

import com.pma.weatherapp.base.functional.Either
import com.pma.weatherapp.base.model.geocoding.Geocoding
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks
import retrofit2.Call
import retrofit2.Response

class GeocodingDataSourceTest {

    @Mock
    lateinit var apiService: GeocodingApiService
    @Mock
    lateinit var getCityNameByCordinatesCall: Call<Geocoding>

    lateinit var dataSource: GeocodingDataSource

    @Before
    fun setUp(){
        openMocks(this)
        dataSource = GeocodingDataSource(apiService)
    }

    @Test
    fun `test getCityNameByCordinates, has response, Success retuned`() = runBlocking {

        val expectedCityNameByCordinates = Geocoding()
        val expectedResult = Either.Success(expectedCityNameByCordinates)

        `when`(apiService.getCoordinatesByCityName(anyString(), anyString(), anyInt())).thenReturn(getCityNameByCordinatesCall)
        `when`(getCityNameByCordinatesCall.execute()).thenReturn(Response.success(expectedCityNameByCordinates))

        val result = dataSource.getCoordinatesByCityName("Arilje", 0)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `test expectedCityNameByCordinates, has error, Error retuned`() = runBlocking {

        val expectedResponseBody = ResponseBody.create(
            MediaType.parse("aplication "), " "
        )


        `when`(apiService.getCoordinatesByCityName(anyString(), anyString(), anyInt())).thenReturn(getCityNameByCordinatesCall)
        `when`(getCityNameByCordinatesCall.execute()).thenReturn(Response.error(400, expectedResponseBody))

        val result = dataSource.getCoordinatesByCityName("Arilje",0)
        assertTrue(result is Either.Error)
    }
}