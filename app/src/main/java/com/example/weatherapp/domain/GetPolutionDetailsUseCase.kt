package com.example.weatherapp.domain

import com.example.weatherapp.arch.UseCase
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.network.WeatherApiRepository
import com.example.weatherapp.domain.model.PolutionDetails
import javax.inject.Inject

class GetPolutionDetailsUseCase @Inject constructor(
    private val weatherApiRepositoryImpl: WeatherApiRepository
): UseCase<List<Any>, Resource<PolutionDetails>> {

    override suspend fun run(input: List<Any>): Resource<PolutionDetails> {
        val polutionDetails =
            weatherApiRepositoryImpl.getPolutionDetails(
                input[0] as Double,
                input[1] as Double,
                input[2] as String,
                input[3] as String
            )

        return when (polutionDetails) {
            is Resource.Success -> Resource.Success(polutionDetails.data)
            is Resource.Error -> Resource.Error(polutionDetails.message)
        }
    }
}