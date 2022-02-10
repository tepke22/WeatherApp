package com.pma.weatherapp.ui.weather_daily

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.pma.weatherapp.R
import com.pma.weatherapp.base.model.weather.Daily
import kotlinx.android.synthetic.main.fragment_weather_current.*
import kotlinx.android.synthetic.main.weather_daily_item.view.*
import java.lang.Math.round
import java.util.*

class DailyView(contex: Context) : ConstraintLayout(contex){

    private val view = View.inflate(contex, R.layout.weather_daily_item, this)

    fun bind(daily : Daily){
        view.textDay.text = getDayName(daily.dt)
        view.textDate.text = getDateTime(daily.dt)
        view.weatherDescription.text = daily.weather.get(0).description.capitalize()
        view.textMax.text = round(daily.temp.max).toString() + " °C"
        view.textMin.text = round(daily.temp.min).toString() + " °C"
        Glide.with(this).load("https://openweathermap.org/img/wn/"+ (daily?.weather?.get(0)?.icon
            ?: "01d") +"@2x.png").into(weatherImage)

    }
    private fun getDayName(dt: Int?): String? {
        try {
            val sdf = SimpleDateFormat("EEEE", Locale.US)
            val netDate = Date(dt?.toLong()?.times(1000) ?: -1)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
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
