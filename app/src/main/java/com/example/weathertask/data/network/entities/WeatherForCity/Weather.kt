package com.example.weathertask.data.network.entities.WeatherForCity

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)