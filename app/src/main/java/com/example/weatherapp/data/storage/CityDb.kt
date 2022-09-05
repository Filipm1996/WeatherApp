package com.example.weatherapp.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.storage.dao.CityDao
import com.example.weatherapp.data.storage.entities.CityModel

@Database(
    entities = [CityModel::class],
    version = 1
)
abstract class CityDb : RoomDatabase() {

    abstract fun cityDao(): CityDao
}