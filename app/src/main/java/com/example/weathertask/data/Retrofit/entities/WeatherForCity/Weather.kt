package com.example.weathertask.data.Retrofit.entities.WeatherForCity

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)