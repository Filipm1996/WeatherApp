package com.example.weatkertask.data.repositories

import androidx.lifecycle.LiveData
import com.example.weatkertask.common.Resource
import com.example.weatkertask.data.Retrofit.entities.Coordinates.CoordinatesResponse
import com.example.weatkertask.data.Retrofit.entities.WeatherForCity.CurrentWeatherResponse
import com.example.weatkertask.data.db.City
import com.example.weatkertask.data.db.Weather

interface RepositoryDefault {

    suspend fun getCoordinates(cityName: String): Resource<List<City>>

    suspend fun getWeatherForCity(lat : Double , lon : Double,cityName: String) : Resource<Weather>

    fun getCities() : LiveData<List<City>>

    fun deleteCity(cityName:String)

    fun insertCity(city: City)

    suspend fun ifCityExists(cityName: String) : Boolean
}