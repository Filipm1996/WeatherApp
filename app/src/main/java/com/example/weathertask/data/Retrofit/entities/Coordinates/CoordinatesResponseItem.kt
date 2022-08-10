package com.example.weathertask.data.Retrofit.entities.Coordinates

data class CoordinatesResponseItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)