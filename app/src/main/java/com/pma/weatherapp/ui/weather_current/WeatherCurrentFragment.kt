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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.pma.weatherapp.R
import com.pma.weatherapp.base.data.ApiServiceProvider
import com.pma.weatherapp.base.data.airpollution_api.AirPollutionDataSource
import com.pma.weatherapp.base.data.geocoding_api.GeocodingDataSource
import com.pma.weatherapp.base.data.weather_api.WeatherDataSource
import com.pma.weatherapp.base.functional.Either
import com.pma.weatherapp.base.functional.ViewModelFactoryUtil
import com.pma.weatherapp.base.functional.WeatherViewState
import com.pma.weatherapp.base.model.air_pollution.AirPollution
import com.pma.weatherapp.base.model.air_pollution.Coord
import com.pma.weatherapp.base.model.geocoding.Geocoding
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
    private lateinit var currentCoords: Coord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactoryUtil.viewModelFactory {
            WeatherCurrentViewModel(WeatherDataSource(ApiServiceProvider.weatherApiService))
        })[WeatherCurrentViewModel::class.java]

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

        viewModel.state.observe(viewLifecycleOwner) { state ->

            // breweryDetailsProgressBar.isVisible = state is BreweryDetailsViewState.Processing

            when (state) {
                is WeatherViewState.DataReceived -> state.weatherInfo.daily?.get(0)
                    ?.let { setUpView(state.weatherInfo.current, state.weatherInfo.alerts, it) }
                is WeatherViewState.ErrorReceived -> showError(state.message)
            }
        }

        currentCoords = Coord(
            sharedPreferences.getFloat("lat", 43.899998F).toDouble(),
            sharedPreferences.getFloat("lon", 20.390945F).toDouble()
        )

        viewModel.getCurrentWeather(currentCoords.lat, currentCoords.lon)

    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setUpView(current: Current?, alert: List<Alert>?, today: Daily) {

        Log.d("TAGGG LAT", currentCoords.lat.toString())
        Log.d("TAGGG LON", currentCoords.lon.toString())

        val geocodingApi =
            GeocodingDataSource(ApiServiceProvider.geocodingApiService)
        lifecycleScope.launch {
            var geocoding: Geocoding? = null
            when (val result =
                geocodingApi.getCityNameByCordinates(currentCoords.lat, currentCoords.lon)) {
                is Either.Success -> geocoding = result.data
                is Either.Error -> showError(result.exception.toString())
            }
            if (geocoding != null) {
                textCity.text = geocoding.toString()
            }
        }

        textDegree.text = getString(R.string.curr_temp, current?.temp?.let { round(it) })
        textReelFeel.text = getString(R.string.real_feel, current?.feels_like?.let { round(it) })
        Glide.with(this).load(
            "https://openweathermap.org/img/wn/" + (current?.weather?.get(0)?.icon
                ?: "01d") + "@2x.png"
        ).into(weatherImage)
        textDate.text = getDateTime(current?.dt)
        textMax.text = getString(R.string.max_temp, round(today.temp.max))
        textMin.text = getString(R.string.min_temp, round(today.temp.min))
        humidity.text = getString(R.string.humidity_value, current?.humidity)
        wind.text = getString(R.string.wind_value, current?.wind_speed)
        weatherDescription.text = current?.weather?.get(0)?.description?.capitalize(Locale.ROOT)

        val airPollutionApi =
            AirPollutionDataSource(ApiServiceProvider.airPollutionApiService)
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
        return try {
            val sdf = SimpleDateFormat("dd.MM.yyyy.", Locale.ENGLISH)
            val netDate = Date(dt?.toLong()?.times(1000) ?: -1)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

}