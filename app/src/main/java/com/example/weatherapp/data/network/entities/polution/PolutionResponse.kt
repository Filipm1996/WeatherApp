package com.example.weatherapp.data.network.entities.polution


data class PolutionResponse(
    val coord: Coord,
    val list: List<Item>
)