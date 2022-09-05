package com.example.weatherapp.data.network.api

import com.example.weatherapp.data.network.entities.coordinates.CoordinatesResponse
import com.example.weatherapp.data.network.entities.weather.current.CurrentWeatherResponse
import com.example.weatherapp.data.network.entities.weather.hourly.HourlyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("geo/1.0/direct?limit=5")
    suspend fun getCities(@Query("q") cityName: String): CoordinatesResponse

    @GET("data/2.5/weather?units=metric")
    suspend fun getWeatherForCity(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): CurrentWeatherResponse

    @GET("data/2.5/forecast?units=metric")
    suspend fun getHourlyWeatherForCity(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
    ) : HourlyResponse
}