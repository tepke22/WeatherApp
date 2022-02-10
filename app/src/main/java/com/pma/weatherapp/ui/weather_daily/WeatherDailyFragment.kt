package com.pma.weatherapp.ui.weather_daily

import android.os.Bundle
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
import com.pma.weatherapp.base.model.weather.Alert
import com.pma.weatherapp.base.model.weather.Current
import com.pma.weatherapp.base.model.weather.Daily
import com.pma.weatherapp.ui.weather_current.WeatherCurrentViewModel
import kotlinx.android.synthetic.main.fragment_weather_current.*
import kotlinx.android.synthetic.main.fragment_weather_daily.*
import kotlin.math.round

class WeatherDailyFragment : Fragment() {
    private lateinit var viewModel: WeatherDailyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactoryUtil.viewModelFactory {
            WeatherDailyViewModel(WeatherDataSource(ApiServiceProvider.weatherApiService))
        }).get(WeatherDailyViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_daily, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->

            // breweryDetailsProgressBar.isVisible = state is BreweryDetailsViewState.Processing

            when (state) {
                is WeatherViewState.DataReceived -> setUpView(state.weatherInfo.daily)
                is WeatherViewState.ErrorReceived -> showError(state.message)
            }
        })
        viewModel.getDailyWeather()
    }
    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private  fun setUpView(days: List<Daily>?){
        days?.forEach { day ->
            val view = DailyView(requireContext())
            view.bind(day)
            dailyLayout.addView(view)
        }
    }
}