package com.example.weatherapp.data.network

import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.model.City
import com.example.weatherapp.domain.model.HourlyWeather
import com.example.weatherapp.domain.model.Weather

interface WeatherApiRepository {

    suspend fun getCities(cityName: String): Resource<List<City>>

    suspend fun getWeatherForCity(
        lat: Double,
        lon: Double,
        cityName: String
    ): Resource<Weather>

    suspend fun getHourlyWeatherForCity(
        lat: Double,
        lon: Double,
        cityName: String
    ): Resource<List<HourlyWeather>>
}