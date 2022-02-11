package com.pma.weatherapp.ui.weather_hourly.recycler

import android.icu.text.SimpleDateFormat
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pma.weatherapp.base.model.weather.Hourly
import kotlinx.android.synthetic.main.weather_hourly_item.view.*
import java.lang.Math.round
import java.util.*

class WeatherHourlyRVViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(hour : Hourly){
        itemView.textHour.text = getTime(hour.dt)
        itemView.textDayHourly.text = getDay(hour.dt)
        itemView.textHumidity.text = hour.humidity.toString() + " %"
        itemView.WindSpeedTxt.text = hour.wind_speed.toString() + " m/s"
        itemView.textDescription.text = hour.weather?.get(0)?.description?.capitalize() ?: ""
        Glide.with(itemView).load("https://openweathermap.org/img/wn/"+ (hour?.weather?.get(0)?.icon
            ?: "01d") +"@2x.png").into(itemView.weatherImageHourly)
        itemView.textTemperature.text = round(hour.temp).toString() + " °C"
    }

    private fun getTime(dt: Int?): String? {
        try {
            val sdf = SimpleDateFormat("HH:mm")
            val netDate = Date(dt?.toLong()?.times(1000) ?: -1)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    private fun getDay(dt: Int?): String? {
        try {
            val sdf = SimpleDateFormat("EEEE", Locale.US)
            val netDate = Date(dt?.toLong()?.times(1000) ?: -1)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}