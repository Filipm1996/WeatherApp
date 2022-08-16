package com.example.weathertask.domain

import com.example.weathertask.arch.UseCase
import com.example.weathertask.common.Resource
import com.example.weathertask.data.network.WeatherApiRepository
import com.example.weathertask.domain.model.City
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val weatherApiRepository: WeatherApiRepository
) : UseCase<String, Resource<List<City>>> {

    override suspend fun run(input: String) =
        weatherApiRepository.getCities(input)
}