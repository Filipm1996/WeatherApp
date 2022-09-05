package com.example.weatherapp.data.network.entities.weather.current

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)