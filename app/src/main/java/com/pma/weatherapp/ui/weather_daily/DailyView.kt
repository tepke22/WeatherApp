package com.pma.weatherapp.ui.weather_daily

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.pma.weatherapp.R
import com.pma.weatherapp.base.model.weather.Daily
import kotlinx.android.synthetic.main.weather_daily_item.view.*
import java.util.*

class DailyView(contex: Context) : ConstraintLayout(contex){

    private val view = View.inflate(contex, R.layout.weather_daily_item, this)

    fun bind(daily : Daily){
        view.textDay.text = getDayName(daily.dt)
        view.textDate.text = getDateTime(daily.dt)
        view.weatherDescription.text = daily.weather[0].description.capitalize(Locale.ROOT)
        view.textMax.text = resources.getString(R.string.max_temp, daily.temp.max)
        view.textMin.text = resources.getString(R.string.min_temp, daily.temp.min)
        Glide.with(this).load(
            "https://openweathermap.org/img/wn/" + daily.weather[0].icon + "@2x.png"
        ).into(weatherImage)

    }
    private fun getDayName(dt: Int?): String? {
        return try {
            val sdf = SimpleDateFormat("EEEE", Locale.US)
            val netDate = Date(dt?.toLong()?.times(1000) ?: -1)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
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
