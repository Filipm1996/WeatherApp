package com.example.weatherapp.domain

import com.example.weatherapp.arch.UseCase
import com.example.weatherapp.data.storage.WeatherDbRepository
import com.example.weatherapp.domain.model.City
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