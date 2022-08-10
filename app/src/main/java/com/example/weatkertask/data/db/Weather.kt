package com.example.weatkertask.data.db

import java.io.Serializable


data class Weather (
    val cityName: String,
    val description : String,
    val icon : String,
    val visibility : Int,
    val windSpeed : Double,
    val cloudiness : Int,
    val temp : Int,
    val tempFell : Int,
    val pressure : Int,
    val humidity : Int
) : Serializable
