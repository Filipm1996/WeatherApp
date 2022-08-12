package com.example.weathertask.data.storage

import androidx.lifecycle.MutableLiveData
import com.example.weathertask.data.storage.entities.CityModel
import com.example.weathertask.domain.model.City

interface WeatherDbRepositoryDefault {

    suspend fun getCities(): List<City>

    fun deleteCity(cityName: String)

    fun insertCity(city: City)

    suspend fun ifCityExists(cityName: String): Boolean

}