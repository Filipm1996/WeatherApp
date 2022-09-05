package com.example.weatherapp.domain.model

import com.example.weatherapp.data.storage.entities.CityModel

data class City(
    val lat: Double,
    val lon: Double,
    val name: String,
    val country: String
) {

    fun toCityModel(): CityModel {
        return CityModel(lat, lon, name, country)
    }
}
