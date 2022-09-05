package com.example.weatherapp.data.storage

import com.example.weatherapp.data.storage.dao.CityDao
import com.example.weatherapp.domain.model.City
import javax.inject.Inject

class WeatherDbRepositoryImpl @Inject constructor(
    private val cityDao: CityDao
) : WeatherDbRepository {

    override suspend fun getCities(): List<City> =
        cityDao.getAllCities().map { it.map() }

    override suspend fun deleteCity(cityName: String) =
        cityDao.deleteCityByName(cityName)

    override suspend fun insertCity(city: City) =
        cityDao.insertCity(city.toCityModel())

    override suspend fun isCityAlreadyAdded(
        lat: Double,
        lon: Double
    ): Boolean = cityDao.exists(lat, lon)
}