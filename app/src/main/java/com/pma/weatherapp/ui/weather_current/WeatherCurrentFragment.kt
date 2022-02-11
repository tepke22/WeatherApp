package com.pma.weatherapp.ui.weather_current

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.pma.weatherapp.R
import com.pma.weatherapp.base.data.ApiServiceProvider
import com.pma.weatherapp.base.data.airpollution_api.AirPollutionDataSource
import com.pma.weatherapp.base.data.weather_api.WeatherDataSource
import com.pma.weatherapp.base.functional.Either
import com.pma.weatherapp.base.functional.ViewModelFactoryUtil
import com.pma.weatherapp.base.functional.WeatherViewState
import com.pma.weatherapp.base.model.air_pollution.AirPollution
import com.pma.weatherapp.base.model.weather.Alert
import com.pma.weatherapp.base.model.weather.Current
import com.pma.weatherapp.base.model.weather.Daily
import kotlinx.android.synthetic.main.fragment_weather_current.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.round

class WeatherCurrentFragment : Fragment() {

    private lateinit var viewModel: WeatherCurrentViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactoryUtil.viewModelFactory {
            WeatherCurrentViewModel(WeatherDataSource(ApiServiceProvider.weatherApiService))
        }).get(WeatherCurrentViewModel::class.java)

        sharedPreferences = this.requireActivity().getPreferences(MODE_PRIVATE)
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
                is WeatherViewState.DataReceived -> state.weatherInfo.daily?.get(0)
                    ?.let { setUpView(state.weatherInfo.current, state.weatherInfo.alerts, it) }
                is WeatherViewState.ErrorReceived -> showError(state.message)
            }
        })
        viewModel.getCurrentWeather(
            sharedPreferences.getFloat("lat", 43.899998F).toDouble(),
            sharedPreferences.getFloat("lon", 20.390945F).toDouble()
        )

    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setUpView(current: Current?, alert: List<Alert>?, today: Daily) {

        Log.d("TAGGG LAT", sharedPreferences.getFloat("lat", (-5.0).toFloat()).toString())
        Log.d("TAGGG LON", sharedPreferences.getFloat("lon", (-5.0).toFloat()).toString())

        textCity.text = "Čačak, RS"
        textDegree.text = current?.temp?.let { round(it).toString() } + " °C"
        textReelFeel.text =
            "Reel feel: " + current?.feels_like?.let { round(it).toString() } + " °C"
        Glide.with(this).load(
            "https://openweathermap.org/img/wn/" + (current?.weather?.get(0)?.icon
                ?: "01d") + "@2x.png"
        ).into(weatherImage)
        textDate.text = getDateTime(current?.dt)
        textMax.text = "Max: " + round(today.temp.max).toString() + " °C"
        textMin.text = "Min: " + round(today.temp.min).toString() + " °C"
        humidity.text = "Humidity: " + current?.humidity.toString() + "%"
        wind.text = "Wind: " + current?.wind_speed.toString() + "m/s"
        weatherDescription.text = current?.weather?.get(0)?.description?.capitalize()

        val airPollutionApi: AirPollutionDataSource =
            AirPollutionDataSource(ApiServiceProvider.airPollutionApiService);
        lifecycleScope.launch {
            var airPollution: AirPollution? = null
            when (val result = airPollutionApi.getCurrentAirPollution(0.0, 0.0)) {
                is Either.Success -> airPollution = result.data
                is Either.Error -> showError(result.exception.toString())
            }
            airPollutionValue.text = airPollution.toString()
        }

        alerts.text = alert?.get(0)?.description ?: "There are no country alerts currently."

    }

    private fun getDateTime(dt: Int?): String? {
        try {
            val sdf = SimpleDateFormat("dd.MM.yyyy.")
            val netDate = Date(dt?.toLong()?.times(1000) ?: -1)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}