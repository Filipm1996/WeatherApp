package com.example.weatherapp.data.network.entities.weather.hourly

import com.example.weatherapp.domain.model.HourlyWeather

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)