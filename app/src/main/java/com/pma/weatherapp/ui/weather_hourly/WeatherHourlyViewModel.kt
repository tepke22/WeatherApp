package com.pma.weatherapp.ui.weather_hourly

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pma.weatherapp.base.data.weather_api.WeatherDataSource
import com.pma.weatherapp.base.functional.Either
import com.pma.weatherapp.base.functional.ICoroutineContextProvider
import com.pma.weatherapp.base.functional.WeatherViewState
import kotlinx.coroutines.launch

class WeatherHourlyViewModel(
    private val dataSource: WeatherDataSource,
    private val courutineContextProvider: ICoroutineContextProvider
) : ViewModel() {

    private val _state = MutableLiveData<WeatherViewState>()
    val state: LiveData<WeatherViewState>
        get() = _state

    fun getHourlyWeather(){
        viewModelScope.launch(courutineContextProvider.io) {
            _state.postValue(WeatherViewState.Processing)

            _state.postValue(
                when (val result = dataSource.getHourlyWeather(0.0,0.0)) {
                    is Either.Success -> WeatherViewState.DataReceived(result.data)
                    is Either.Error -> WeatherViewState.ErrorReceived(result.exception.toString())
                }
            )

        }
    }
}