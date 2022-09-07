package com.example.weatherapp.data.network

import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.network.api.ApiService
import com.example.weatherapp.domain.model.City
import com.example.weatherapp.domain.model.HourlyWeather
import com.example.weatherapp.domain.model.PolutionDetails
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.utils.orErrorText
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

    override suspend fun getHourlyWeatherForCity(
        lat: Double,
        lon: Double,
        cityName: String
    ): Resource<List<HourlyWeather>> {
        return try {
            val result = apiService.getHourlyWeatherForCity(lat, lon)
            Resource.Success(result.list.map { it.map(cityName) })
        } catch (e: Exception) {
            Resource.Error(e.message.orErrorText())
        }
    }

    override suspend fun getPolutionDetails(
        lat: Double,
        lon: Double,
        start: String,
        end: String
    ): Resource<PolutionDetails> {
        return try {
            val result = apiService.getPolutionDetails(lat, lon,start,end)
            Resource.Success(result.list[0].map())
        } catch (e: Exception) {
            Resource.Error(e.message.orErrorText())
        }
    }
}
