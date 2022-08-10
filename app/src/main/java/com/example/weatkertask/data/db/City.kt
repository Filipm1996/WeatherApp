package com.example.weatkertask.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    val lat : Double,
    val lon : Double,
    val name : String,
    val country : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null
)
