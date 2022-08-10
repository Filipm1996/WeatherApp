package com.example.weathertask.data.repositories

import androidx.lifecycle.LiveData
import com.example.weathertask.common.Resource
import com.example.weathertask.data.db.City
import com.example.weathertask.data.db.Weather

interface RepositoryDefault {

    suspend fun getCoordinates(cityName: String): Resource<List<City>>

    suspend fun getWeatherForCity(lat : Double , lon : Double,cityName: String) : Resource<Weather>

    fun getCities() : LiveData<List<City>>

    fun deleteCity(cityName:String)

    fun insertCity(city: City)

    suspend fun ifCityExists(cityName: String) : Boolean
}