package com.example.weathertask.data.storage

import com.example.weathertask.domain.model.City

interface WeatherDbRepository {

    suspend fun getCities(): List<City>

    suspend fun deleteCity(cityName: String)

    suspend fun insertCity(city: City)

    suspend fun isCityAlreadyAdded(lat: Double, lon: Double): Boolean
}