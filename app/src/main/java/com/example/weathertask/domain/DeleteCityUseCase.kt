package com.example.weathertask.domain

import com.example.weathertask.arch.UseCase
import com.example.weathertask.data.storage.WeatherDbRepository
import javax.inject.Inject

class DeleteCityUseCase @Inject constructor(
    private val weatherDbRepository: WeatherDbRepository
) : UseCase<String, Unit> {

    override suspend fun run(input: String) =
        weatherDbRepository.deleteCity(input)
}