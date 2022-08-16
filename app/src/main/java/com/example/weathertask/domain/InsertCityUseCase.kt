package com.example.weathertask.domain

import com.example.weathertask.arch.UseCase
import com.example.weathertask.data.storage.WeatherDbRepository
import com.example.weathertask.domain.model.City
import javax.inject.Inject

class InsertCityUseCase @Inject constructor(
    private val weatherDbRepository: WeatherDbRepository
) : UseCase<City, Boolean> {

    override suspend fun run(input: City) =
        if (weatherDbRepository.isCityAlreadyAdded(input.lat, input.lon)) {
            false
        } else {
            weatherDbRepository.insertCity(input)
            true
        }
}