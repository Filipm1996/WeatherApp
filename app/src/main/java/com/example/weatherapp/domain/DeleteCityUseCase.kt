package com.example.weatherapp.domain

import com.example.weatherapp.arch.UseCase
import com.example.weatherapp.data.storage.WeatherDbRepository
import javax.inject.Inject

class DeleteCityUseCase @Inject constructor(
    private val weatherDbRepository: WeatherDbRepository
) : UseCase<String, Unit> {

    override suspend fun run(input: String) =
        weatherDbRepository.deleteCity(input)
}