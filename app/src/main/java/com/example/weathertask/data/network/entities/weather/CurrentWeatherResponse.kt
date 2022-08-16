package com.example.weathertask.data.network.entities.weather

import java.math.BigDecimal
import java.math.RoundingMode

data class CurrentWeatherResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
) {

    fun map() =
            com.example.weathertask.domain.model.Weather(
                name,
                weather[0].description,
                weather[0].icon,
                visibility,
                wind.speed,
                clouds.all,
                BigDecimal(main.temp).setScale(0, RoundingMode.HALF_DOWN).toInt(),
                BigDecimal(main.feels_like).setScale(0, RoundingMode.HALF_DOWN).toInt(),
                main.pressure,
                main.humidity
            )
}