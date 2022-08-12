package com.example.weathertask.data.network

import com.example.weathertask.common.Resource
import com.example.weathertask.data.network.api.ApiService
import com.example.weathertask.data.network.entities.Coordinates.CoordinatesResponse
import com.example.weathertask.data.network.entities.WeatherForCity.CurrentWeatherResponse
import com.example.weathertask.data.storage.entities.CityModel
import com.example.weathertask.domain.model.City
import com.example.weathertask.domain.model.Weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.lang.Exception
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class WeatherApiRepository @Inject constructor(
    private val apiService: ApiService,
) : WeatherApiRepositoryDefault {

    override suspend fun getCoordinates(cityName: String): Resource<List<City>> {
        return try {
            val coordinatesResponse: CoordinatesResponse = CoroutineScope(Dispatchers.IO).async {
                apiService.getCoordinates(cityName)
            }.await()
            val listOfCities = mutableListOf<City>()
            coordinatesResponse.forEach {
                val city = City(it.lat, it.lon, it.name, it.country)
                listOfCities.add(city)
            }
            Resource.Success(listOfCities)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error")
        }
    }

    override suspend fun getWeatherForCity(
        lat: Double,
        lon: Double,
        cityName: String
    ): Resource<Weather> {
        return try {
            val currentWeatherResponse: CurrentWeatherResponse =
                CoroutineScope(Dispatchers.IO).async {
                    apiService.getWeatherForCity(lat, lon)
                }.await()
            var weather: Weather
            currentWeatherResponse.let {
                weather = Weather(
                    cityName,
                    it.weather[0].description,
                    it.weather[0].icon,
                    it.visibility,
                    it.wind.speed,
                    it.clouds.all,
                    BigDecimal(it.main.temp).setScale(0, RoundingMode.HALF_DOWN).toInt(),
                    BigDecimal(it.main.feels_like).setScale(0, RoundingMode.HALF_DOWN).toInt(),
                    it.main.pressure,
                    it.main.humidity
                )
            }
            Resource.Success(weather)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error")
        }
    }
}
