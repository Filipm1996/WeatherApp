package com.example.weatherapp.data.network.entities.weather.hourly

import com.example.weatherapp.domain.model.HourlyWeather

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
            main.feels_like,
            main.temp,
            main.pressure,
            main.humidity,
            dt_txt
            )
}