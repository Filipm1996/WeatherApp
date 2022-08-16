package com.example.weathertask.data.network

import com.example.weathertask.common.Resource
import com.example.weathertask.data.network.api.ApiService
import com.example.weathertask.domain.model.City
import com.example.weathertask.domain.model.Weather
import com.example.weathertask.utils.orErrorText
import javax.inject.Inject

class WeatherApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : WeatherApiRepository {

    override suspend fun getCities(cityName: String): Resource<List<City>> {
        return try {
            val result = apiService.getCities(cityName).map { it.map() }
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message.orErrorText())
        }
    }

    override suspend fun getWeatherForCity(
        lat: Double,
        lon: Double,
        cityName: String
    ): Resource<Weather> {
        return try {
            val result = apiService.getWeatherForCity(lat, lon)
            Resource.Success(result.map())
        } catch (e: Exception) {
            Resource.Error(e.message.orErrorText())
        }
    }
}
