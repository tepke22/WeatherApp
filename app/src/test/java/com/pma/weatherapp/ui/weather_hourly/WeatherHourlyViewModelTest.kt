package com.pma.weatherapp.ui.weather_hourly

import androidx.lifecycle.Observer
import com.pma.weatherapp.base.data.weather_api.IWeatherDataSource
import com.pma.weatherapp.base.functional.Either
import com.pma.weatherapp.base.InstantExecutorTest
import com.pma.weatherapp.base.TestCoroutineContextProvider
import com.pma.weatherapp.base.functional.WeatherViewState
import com.pma.weatherapp.base.model.weather.WeatherInfo
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.openMocks
import java.lang.Exception

class WeatherHourlyViewModelTest : InstantExecutorTest(){

    @Mock
    lateinit var dataSource: IWeatherDataSource
    @Mock
    lateinit var stateObserver: Observer<WeatherViewState>

    lateinit var  viewModel: WeatherHourlyViewModel

    @Before
    fun setUp(){
        openMocks(this)
        viewModel = WeatherHourlyViewModel(dataSource, TestCoroutineContextProvider())
        viewModel.state.observeForever(stateObserver)
    }

    @Test
    fun `test getHourlyWeather, has result, state changed to Processing - DataRecived`() = runBlocking{

        val expectedResult = WeatherInfo()
        `when`(dataSource.getHourlyWeather(0.0,0.0, "metric")).thenReturn(Either.Success(expectedResult))

        viewModel.getHourlyWeather(0.0,0.0)

        verify(stateObserver).onChanged(WeatherViewState.Processing)
        verify(stateObserver).onChanged(WeatherViewState.DataReceived(expectedResult))
    }

    @Test
    fun `test getHourlyWeather, has error, state changed to Processing - ErrorRecived`() = runBlocking {
        val expectedError = Exception()
        `when`(dataSource.getHourlyWeather(0.0, 0.0, "metric")).thenReturn(Either.Error(expectedError))

        viewModel.getHourlyWeather(0.0,0.0)

        verify(stateObserver).onChanged(WeatherViewState.Processing)
        verify(stateObserver).onChanged(WeatherViewState.ErrorReceived(expectedError.toString()))
    }
}