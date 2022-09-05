package com.example.weatherapp.data.network.entities.weather.hourly

import com.example.weatherapp.domain.model.HourlyWeather
import java.math.BigDecimal
import java.math.RoundingMode

data class Item (
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
) {
    fun map(cityName : String) : HourlyWeather =
        HourlyWeather(
            cityName,
            weather[0].description,
            weather[0].icon,
            visibility,
            wind.speed,
            clouds.all,
            BigDecimal(main.temp).setScale(0, RoundingMode.HALF_DOWN).toInt(),
            BigDecimal(main.feels_like).setScale(0, RoundingMode.HALF_DOWN).toInt(),
            main.pressure,
            main.humidity,
            dt_txt
            )
}