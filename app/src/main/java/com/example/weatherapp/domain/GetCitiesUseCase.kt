package com.example.weatherapp.domain

import com.example.weatherapp.arch.UseCase
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.network.WeatherApiRepository
import com.example.weatherapp.domain.model.City
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val weatherApiRepository: WeatherApiRepository
) : UseCase<String, Resource<List<City>>> {

    override suspend fun run(input: String) =
        weatherApiRepository.getCities(input)
}