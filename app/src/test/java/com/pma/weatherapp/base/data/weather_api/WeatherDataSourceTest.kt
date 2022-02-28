package com.pma.weatherapp.base.data.weather_api
import com.pma.weatherapp.base.functional.Either
import com.pma.weatherapp.base.model.weather.WeatherInfo
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks
import retrofit2.Call
import retrofit2.Response

class WeatherDataSourceTest {

    @Mock
    lateinit var apiService: WeatherApiService
    @Mock
    lateinit var getCurrentWeatherCall: Call<WeatherInfo>

    lateinit var dataSource: WeatherDataSource

    @Before
    fun setUp(){
        openMocks(this)
        dataSource = WeatherDataSource(apiService)
    }

    @Test
    fun `test getCurrentWeather, has response, Success retuned`() = runBlocking {

        val expectedWeather = WeatherInfo()
        val expectedResult = Either.Success(expectedWeather)

        `when`(apiService.getCurrentWeather(anyDouble(), anyDouble(), anyString(), anyString())).thenReturn(getCurrentWeatherCall)
        `when`(getCurrentWeatherCall.execute()).thenReturn(Response.success(expectedWeather))

        val result = dataSource.getCurrentWeather(0.0,0.0, "metric")

        assertEquals(expectedResult, result)
    }

    @Test
    fun `test getCurrentWeather, has error, Error retuned`() = runBlocking {

        val expectedResponseBody = ResponseBody.create(
            MediaType.parse("aplication "), " "
        )

        `when`(apiService.getCurrentWeather(anyDouble(), anyDouble(), anyString(), anyString())).thenReturn(getCurrentWeatherCall)
        `when`(getCurrentWeatherCall.execute()).thenReturn(Response.error(400, expectedResponseBody))

        val result = dataSource.getCurrentWeather(0.0,0.0, "metric")
        assertTrue(result is Either.Error)
    }
}