package com.pma.weatherapp.ui.weather_daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pma.weatherapp.base.data.weather_api.WeatherDataSource
import com.pma.weatherapp.base.functional.Either
import com.pma.weatherapp.base.functional.WeatherViewState
import kotlinx.coroutines.launch

class WeatherDailyViewModel(private val dataSource: WeatherDataSource) : ViewModel(){
    private val _state = MutableLiveData<WeatherViewState>()
    val state: LiveData<WeatherViewState>
        get() = _state

    fun getDailyWeather(){
        viewModelScope.launch {
            _state.postValue(WeatherViewState.Processing)

            _state.postValue(
                when (val result = dataSource.getDailyWeather(0.0,0.0)) {
                    is Either.Success -> WeatherViewState.DataReceived(result.data)
                    is Either.Error -> WeatherViewState.ErrorReceived(result.exception.toString())
                }
            )

        }
    }
}