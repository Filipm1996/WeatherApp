package com.example.weathertask.data.storage

import com.example.weathertask.domain.model.City

interface WeatherDbRepositoryDefault {

    suspend fun getCities(): List<City>

    fun deleteCity(cityName: String)

    fun insertCity(city: City)

    suspend fun ifCityExists(cityName: String): Boolean

}