package com.pma.weatherapp.ui.weather_daily

import androidx.lifecycle.Observer
import com.pma.weatherapp.base.InstantExecutorTest
import com.pma.weatherapp.base.TestCoroutineContextProvider
import com.pma.weatherapp.base.data.weather_api.IWeatherDataSource
import com.pma.weatherapp.base.functional.Either
import com.pma.weatherapp.base.functional.WeatherViewState
import com.pma.weatherapp.base.model.weather.WeatherInfo
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.Exception

class WeatherDailyViewModelTest : InstantExecutorTest() {
    @Mock
    lateinit var dataSource: IWeatherDataSource

    @Mock
    lateinit var stateObserver: Observer<WeatherViewState>

    lateinit var viewModel: WeatherDailyViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = WeatherDailyViewModel(dataSource, TestCoroutineContextProvider())
        viewModel.state.observeForever(stateObserver)
    }

    @Test
    fun `test getDailyWeather, has result, state changed to Processing - DataRecived`() =
        runBlocking {

            val expectedResult = WeatherInfo()
            Mockito.`when`(dataSource.getDailyWeather(0.0, 0.0, "metric"))
                .thenReturn(Either.Success(expectedResult))

            viewModel.getDailyWeather(0.0, 0.0)

            Mockito.verify(stateObserver).onChanged(WeatherViewState.Processing)
            Mockito.verify(stateObserver).onChanged(WeatherViewState.DataReceived(expectedResult))
        }

    @Test
    fun `test getDailyWeather, has error, state changed to Processing - ErrorRecived`() =
        runBlocking {
            val expectedError = Exception()
            Mockito.`when`(dataSource.getDailyWeather(0.0, 0.0, "metric"))
                .thenReturn(Either.Error(expectedError))

            viewModel.getDailyWeather(0.0, 0.0)

            Mockito.verify(stateObserver).onChanged(WeatherViewState.Processing)
            Mockito.verify(stateObserver)
                .onChanged(WeatherViewState.ErrorReceived(expectedError.toString()))

        }
}