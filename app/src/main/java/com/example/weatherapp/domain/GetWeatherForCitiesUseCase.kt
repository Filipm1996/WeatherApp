package com.example.weatherapp.domain

import com.example.weatherapp.arch.UseCase
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.storage.WeatherDbRepository
import com.example.weatherapp.domain.model.Weather
import javax.inject.Inject

class GetWeatherForCitiesUseCase @Inject constructor(
    private val getWeatherForCityUseCase: GetWeatherForCityUseCase,
    private val weatherDbRepository: WeatherDbRepository
) : UseCase<Unit, Resource<List<Weather>>> {

    override suspend fun run(input: Unit): Resource<List<Weather>> {
        val weatherResultList = weatherDbRepository.getCities()
            .map {
                getWeatherForCityUseCase.run(
                    GetWeatherForCityUseCase.Input(it.lat, it.lon, it.name)
                )
            }

        val doesAllSucceed = weatherResultList.all { it is Resource.Success }

        return if (doesAllSucceed) {
            Resource.Success(
                weatherResultList.map {
                    (it as Resource.Success).data
                })
        } else {
            returnError(weatherResultList)
        }
    }

    private fun returnError(getCitiesResult: List<Resource<Weather>>): Resource<List<Weather>> {
        val errorMessage =
            getCitiesResult.filterIsInstance(Resource.Error::class.java).first().message
        return Resource.Error(errorMessage)
    }
}