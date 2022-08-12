package com.example.weathertask.data.network.entities.coordinates

data class CoordinatesResponseItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)