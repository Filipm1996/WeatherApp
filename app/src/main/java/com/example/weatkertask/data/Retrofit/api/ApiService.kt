package com.example.weatkertask.data.Retrofit.api

import com.example.weatkertask.data.Retrofit.entities.Coordinates.CoordinatesResponse
import com.example.weatkertask.data.Retrofit.entities.WeatherForCity.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("geo/1.0/direct")
    suspend fun getCoordinates(@Query("q") cityName : String, @Query("appid") key :String) : CoordinatesResponse

    @GET("data/2.5/weather?units=metric")
    suspend fun getWeatherForCity(@Query("lat") lat : Double, @Query("lon") lon : Double,@Query("appid") key :String) : CurrentWeatherResponse

}