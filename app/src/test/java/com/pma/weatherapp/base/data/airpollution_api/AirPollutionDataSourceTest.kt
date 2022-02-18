package com.pma.weatherapp.base.data.airpollution_api
import com.pma.weatherapp.base.functional.Either
import com.pma.weatherapp.base.model.air_pollution.AirPollution
import com.pma.weatherapp.base.model.air_pollution.Coord
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response

class AirPollutionDataSourceTest {

    @Mock
    lateinit var apiService: AirPollutionApiService
    @Mock
    lateinit var getCurrentAirPollutionCall: Call<AirPollution>

    lateinit var dataSource: AirPollutionDataSource

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        dataSource = AirPollutionDataSource(apiService)
    }

    @Test
    fun `test getCurrentAirPollution, has response, Success retuned`() = runBlocking {

        val expectedAirPollution = AirPollution(Coord(), listOf())
        val expectedResult = Either.Success(expectedAirPollution)

        Mockito.`when`(apiService.getCurrentAirPollution(anyDouble(), anyDouble(), anyString())).thenReturn(getCurrentAirPollutionCall)
        Mockito.`when`(getCurrentAirPollutionCall.execute()).thenReturn(Response.success(expectedAirPollution))

        val result = dataSource.getCurrentAirPollution(0.0,0.0)

        TestCase.assertEquals(expectedResult, result)
    }

    @Test
    fun `test getCurrentAirPollution, has error, Error retuned`() = runBlocking {

        val expectedResponseBody = ResponseBody.create(
            MediaType.parse("aplication "), " "
        )
        Mockito.`when`(apiService.getCurrentAirPollution(anyDouble(), anyDouble(), anyString())).thenReturn(getCurrentAirPollutionCall)

        Mockito.`when`(getCurrentAirPollutionCall.execute())
            .thenReturn(Response.error(400, expectedResponseBody))

        val result = dataSource.getCurrentAirPollution(0.0,0.0)
        TestCase.assertTrue(result is Either.Error)
    }
}