package com.example.weathertask.data.storage

import com.example.weathertask.data.storage.dao.CityDao
import com.example.weathertask.domain.model.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val cityDao: CityDao) :
    WeatherDbRepositoryDefault {

    override suspend fun getCities(): List<City>  {
        val listOfCities = cityDao.getAllCities()
        val newListOfDomainCities = mutableListOf<City>()
        listOfCities.forEach { newListOfDomainCities.add(it.getCityDomain())}
        return newListOfDomainCities.toList()
    }

    override fun deleteCity(cityName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            cityDao.deleteCityByName(cityName)
        }
    }

    override fun insertCity(city: City) {
        CoroutineScope(Dispatchers.IO).launch { cityDao.insertCity(city.toCityModel()) }
    }

    override suspend fun ifCityExists(cityName: String): Boolean = cityDao.exists(cityName)
}