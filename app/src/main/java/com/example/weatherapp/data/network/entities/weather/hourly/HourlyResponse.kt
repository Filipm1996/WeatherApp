package com.example.weatherapp.data.network.entities.weather.hourly

data class HourlyResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Item>,
    val message: Int
)