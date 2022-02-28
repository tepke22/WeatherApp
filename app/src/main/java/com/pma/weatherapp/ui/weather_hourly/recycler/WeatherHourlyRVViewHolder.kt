package com.pma.weatherapp.ui.weather_hourly.recycler

import android.icu.text.SimpleDateFormat
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pma.weatherapp.R
import com.pma.weatherapp.base.model.weather.Hourly
import kotlinx.android.synthetic.main.weather_hourly_item.view.*
import java.util.*

class WeatherHourlyRVViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(hour: Hourly) {
        itemView.textHour.text = getTime(hour.dt)
        itemView.textDayHourly.text = getDay(hour.dt)
        itemView.textHumidity.text =
            itemView.context.resources.getString(R.string.humidity_value, hour.humidity)
        itemView.windSpeedTxt.text =
            itemView.context.resources.getString(R.string.wind_value, hour.wind_speed)
        itemView.textDescription.text = hour.weather?.get(0)?.description?.capitalize(Locale.ROOT)
            ?: ""
        Glide.with(itemView).load(
            "https://openweathermap.org/img/wn/" + (hour.weather?.get(0)?.icon
                ?: "01d") + "@2x.png"
        ).into(itemView.weatherImageHourly)
        itemView.textTemperature.text =
            itemView.context.resources.getString(R.string.curr_temp, hour.temp)
    }

    private fun getTime(dt: Int?): String? {
        return try {
            val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            val netDate = Date(dt?.toLong()?.times(1000) ?: -1)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    private fun getDay(dt: Int?): String? {
        return try {
            val sdf = SimpleDateFormat("EEEE", Locale.US)
            val netDate = Date(dt?.toLong()?.times(1000) ?: -1)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }
}