package com.example.weatherapp.data.network.entities.polution

import com.example.weatherapp.domain.model.PolutionDetails

data class Item(
    val components: Components,
    val dt: Int,
    val main: Main
) {
    fun map(): PolutionDetails {
        return PolutionDetails(
            components.co,
            components.no.toDouble(),
            components.no2,
            components.o3,
            components.so2,
            components.pm2_5,
            components.pm10,
            components.nh3
        )
    }
}
