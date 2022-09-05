package com.example.weatherapp.domain.model

import java.io.Serializable

data class HourlyWeather(
val cityName: String,
val description: String,
val icon: String,
val visibility: Int,
val windSpeed: Double,
val cloudiness: Int,
val temp: Double,
val tempFell: Double,
val pressure: Int,
val humidity: Int,
val timestamp: String
) :Serializable