package com.example.weatherapp.domain

import com.example.weatherapp.arch.UseCase
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.network.WeatherApiRepository
import com.example.weatherapp.domain.model.City
import com.example.weatherapp.domain.model.HourlyWeather
import javax.inject.Inject

class GetHourlyWeatherUseCase @Inject constructor(
    private val weatherApiRepositoryImpl: WeatherApiRepository
) : UseCase<List<Any>, Resource<List<HourlyWeather>>> {

    override suspend fun run(input: List<Any>): Resource<List<HourlyWeather>> {
        val listOfHourlyWeather =
            weatherApiRepositoryImpl.getHourlyWeatherForCity(
                input[1] as Double,
                input[2] as Double,
                input[0] as String
            )

        return when (listOfHourlyWeather) {
            is Resource.Success -> Resource.Success(listOfHourlyWeather.data)
            is Resource.Error -> Resource.Error(listOfHourlyWeather.message)
        }
    }
}