package com.pma.weatherapp.ui.weather_daily

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pma.weatherapp.R
import com.pma.weatherapp.base.data.ApiServiceProvider
import com.pma.weatherapp.base.data.weather_api.WeatherDataSource
import com.pma.weatherapp.base.functional.CoroutineContextProvider
import com.pma.weatherapp.base.functional.ViewModelFactoryUtil
import com.pma.weatherapp.base.functional.WeatherViewState
import com.pma.weatherapp.base.model.weather.Daily
import kotlinx.android.synthetic.main.fragment_weather_daily.*


class WeatherDailyFragment : Fragment() {

    private lateinit var viewModel: WeatherDailyViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactoryUtil.viewModelFactory {
            WeatherDailyViewModel(WeatherDataSource(ApiServiceProvider.weatherApiService),
                CoroutineContextProvider()
            )
        })[WeatherDailyViewModel::class.java]

        sharedPreferences = this.requireActivity().getPreferences(Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_daily, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindFormViewModel()
        viewModel.getDailyWeather(
            sharedPreferences.getFloat("lat", 43.899998F).toDouble(),
            sharedPreferences.getFloat("lon", 20.390945F).toDouble()
        )
    }

    private fun bindFormViewModel() {

        viewModel.state.observe(viewLifecycleOwner) { state ->

            when (state) {
                is WeatherViewState.DataReceived -> setUpView(state.weatherInfo.daily)
                is WeatherViewState.ErrorReceived -> showError(state.message)
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private  fun setUpView(days: List<Daily>?){
        dailyLayout.removeAllViews()
        days?.forEach { day ->
            val view = DailyView(requireContext())
            view.bind(day)
            dailyLayout.addView(view)
        }
    }
}