package com.pma.weatherapp.ui.weather_hourly.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pma.weatherapp.R
import com.pma.weatherapp.base.model.weather.Hourly

class WeatherHourlyRVAdapter(
    private val hours: List<Hourly>

): RecyclerView.Adapter<WeatherHourlyRVViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WeatherHourlyRVViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.weather_hourly_item, parent, false)
    )

    override fun onBindViewHolder(holder: WeatherHourlyRVViewHolder, position: Int) {
        holder.bind(hours[position])
    }

    override fun getItemCount() = hours.size
}