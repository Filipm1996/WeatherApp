package com.example.weathertask.arch.usecase

import com.example.weathertask.common.Resource
import com.example.weathertask.data.storage.entities.CityModel
import com.example.weathertask.domain.model.City
import com.example.weathertask.domain.model.Weather

interface UseCaseDefault  {
    suspend fun getCities(): List<City>

    fun deleteCity(cityName: String)

    fun insertCity(city: City)

    suspend fun ifCityExists(cityName: String): Boolean

    suspend fun getCoordinates(cityName: String): Resource<List<City>>

    suspend fun getWeatherForCity(lat : Double , lon : Double,cityName: String) : Resource<Weather>
}