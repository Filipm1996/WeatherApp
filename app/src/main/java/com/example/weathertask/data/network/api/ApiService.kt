package com.example.weathertask.data.network.api

import com.example.weathertask.data.network.entities.Coordinates.CoordinatesResponse
import com.example.weathertask.data.network.entities.WeatherForCity.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("geo/1.0/direct?limit=5")
    suspend fun getCoordinates(@Query("q") cityName : String) : CoordinatesResponse

    @GET("data/2.5/weather?units=metric")
    suspend fun getWeatherForCity(@Query("lat") lat : Double, @Query("lon") lon : Double) : CurrentWeatherResponse

}