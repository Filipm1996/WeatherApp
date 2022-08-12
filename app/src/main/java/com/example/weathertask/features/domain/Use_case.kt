package com.example.weathertask.features.domain

import com.example.weathertask.arch.usecase.UseCaseDefault
import com.example.weathertask.common.Resource
import com.example.weathertask.data.network.WeatherApiRepositoryDefault
import com.example.weathertask.data.storage.WeatherDbRepositoryDefault
import com.example.weathertask.domain.model.City
import com.example.weathertask.domain.model.Weather
import javax.inject.Inject

class UseCase @Inject constructor(
    private val weatherDbRepository: WeatherDbRepositoryDefault,
    private val weatherApiRepository: WeatherApiRepositoryDefault
) : UseCaseDefault {

    override suspend fun getCities(): List<City> = weatherDbRepository.getCities()


    override fun deleteCity(cityName: String) = weatherDbRepository.deleteCity(cityName)

    override fun insertCity(city: City) = weatherDbRepository.insertCity(city)

    override suspend fun ifCityExists(cityName: String): Boolean =
        weatherDbRepository.ifCityExists(cityName)

    override suspend fun getCoordinates(cityName: String): Resource<List<City>> =
        weatherApiRepository.getCoordinates(cityName)

    override suspend fun getWeatherForCity(
        lat: Double,
        lon: Double,
        cityName: String
    ): Resource<Weather> = weatherApiRepository.getWeatherForCity(lat, lon, cityName)

}