package com.example.weathertask.data.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weathertask.domain.model.City

@Entity
data class CityModel(
    val lat : Double,
    val lon : Double,
    val name : String,
    val country : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null
){
    fun getCityDomain() : City {
        return City(lat,lon,name,country)
    }
}
