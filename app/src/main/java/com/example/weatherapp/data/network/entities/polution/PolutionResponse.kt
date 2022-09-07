package com.example.weatherapp.data.network.entities.polution

data class PolutionResponse(
    val coord: List<Int>,
    val list: List<Item>
)