package com.pma.weatherapp.ui.weather_current

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.pma.weatherapp.R
import com.pma.weatherapp.base.data.ApiServiceProvider
import com.pma.weatherapp.base.data.weather_api.WeatherDataSource
import com.pma.weatherapp.base.functional.ViewModelFactoryUtil
import com.pma.weatherapp.base.functional.WeatherViewState
import com.pma.weatherapp.base.model.weather.Current
import com.pma.weatherapp.base.model.weather.WeatherInfo
import kotlinx.android.synthetic.main.fragment_weather_current.*
import java.util.*
import kotlin.math.round

class WeatherCurrentFragment : Fragment() {

    private lateinit var viewModel: WeatherCurrentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactoryUtil.viewModelFactory {
            WeatherCurrentViewModel(WeatherDataSource(ApiServiceProvider.weatherApiService))
        }).get(WeatherCurrentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_current, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.state.observe(viewLifecycleOwner, Observer { state ->

           // breweryDetailsProgressBar.isVisible = state is BreweryDetailsViewState.Processing

            when (state) {
                is WeatherViewState.DataReceived -> setUpView(state.weatherInfo.current)
                is WeatherViewState.ErrorReceived -> showError(state.message)
            }
        })
        viewModel.getCurrentWeather()
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private  fun setUpView(current : Current?){

        textCity.text = "Čačak, RS"
        textDegree.text = current?.temp?.let { round(it).toString() } + "°C"
        textReelFeel.text = "Reel feel: " + current?.feels_like?.let { round(it).toString() } + "°C"
        Glide.with(this).load("https://openweathermap.org/img/wn/"+ (current?.weather?.get(0)?.icon
            ?: "01d") +"@2x.png").into(weatherImage)
        textDate.text = getDateTime(current?.dt)
        humidity.text = "Humidity: " + current?.humidity.toString() + "%"
        wind.text = "Wind: " + current?.wind_speed.toString() + "m/s"
        air_pollution.text = "Zagadjenost vazduha"
        alerts.text = "Upozorenje na vremenske nepogode u drzavi"

    }


    private fun getDateTime(dt: Int?): String? {
        try {
            val sdf = SimpleDateFormat("dd.MM.yyyy")
            val netDate = Date(dt?.toLong()?.times(1000) ?: -1)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}