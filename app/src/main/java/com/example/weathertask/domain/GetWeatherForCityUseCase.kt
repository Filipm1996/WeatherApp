package com.example.weathertask.domain

import com.example.weathertask.arch.UseCase
import com.example.weathertask.common.Resource
import com.example.weathertask.data.network.WeatherApiRepository
import com.example.weathertask.domain.model.Weather
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