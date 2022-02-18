package com.pma.weatherapp.ui.weather_hourly

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pma.weatherapp.R
import com.pma.weatherapp.base.data.ApiServiceProvider
import com.pma.weatherapp.base.data.weather_api.WeatherDataSource
import com.pma.weatherapp.base.functional.CoroutineContextProvider
import com.pma.weatherapp.base.functional.ViewModelFactoryUtil
import com.pma.weatherapp.base.functional.WeatherViewState
import com.pma.weatherapp.base.model.weather.Hourly
import com.pma.weatherapp.ui.weather_hourly.recycler.WeatherHourlyRVAdapter
import kotlinx.android.synthetic.main.fragment_weather_hourly.*


class WeatherHourlyFragment : Fragment() {

    private lateinit var viewModel: WeatherHourlyViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactoryUtil.viewModelFactory {
            WeatherHourlyViewModel(WeatherDataSource(ApiServiceProvider.weatherApiService),
                CoroutineContextProvider()
            )
        })[WeatherHourlyViewModel::class.java]

        sharedPreferences = this.requireActivity().getPreferences(MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_hourly, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindFormViewModel()
        viewModel.getHourlyWeather(
            sharedPreferences.getFloat("lat", 43.899998F).toDouble(),
            sharedPreferences.getFloat("lon", 20.390945F).toDouble()
        )
    }

    private fun bindFormViewModel() {

        viewModel.state.observe(viewLifecycleOwner) { state ->

            when (state) {
                is WeatherViewState.DataReceived -> state.weatherInfo.hourly?.let {
                    setUpRecyclerView(it)
                }
                is WeatherViewState.ErrorReceived -> showError(state.message)
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    private fun setUpRecyclerView(hours: List<Hourly>) {
        weatherHourlyRV.adapter = WeatherHourlyRVAdapter(hours)
    }




}