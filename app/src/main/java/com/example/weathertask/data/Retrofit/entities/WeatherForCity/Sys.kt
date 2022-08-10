package com.example.weathertask.data.Retrofit.entities.WeatherForCity

data class Sys(
    val country: String,
    val id: Int,
    val message: Double,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)