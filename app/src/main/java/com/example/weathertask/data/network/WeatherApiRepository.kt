package com.example.weathertask.data.network

import com.example.weathertask.common.Resource
import com.example.weathertask.domain.model.City
import com.example.weathertask.domain.model.Weather

interface WeatherApiRepository {

    suspend fun getCities(cityName: String): Resource<List<City>>

    suspend fun getWeatherForCity(
        lat: Double,
        lon: Double,
        cityName: String
    ): Resource<Weather>
}