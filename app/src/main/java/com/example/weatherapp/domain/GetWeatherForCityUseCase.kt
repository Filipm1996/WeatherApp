package com.example.weatherapp.domain

import com.example.weatherapp.arch.UseCase
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.network.WeatherApiRepository
import com.example.weatherapp.domain.model.Weather
import javax.inject.Inject

class GetWeatherForCityUseCase @Inject constructor(
    private val weatherApiRepository: WeatherApiRepository
) : UseCase<GetWeatherForCityUseCase.Input, Resource<Weather>> {

    override suspend fun run(input: Input) =
        weatherApiRepository.getWeatherForCity(input.lat, input.lon, input.cityName)

    data class Input(
        val lat: Double,
        val lon: Double,
        val cityName: String
    )
}