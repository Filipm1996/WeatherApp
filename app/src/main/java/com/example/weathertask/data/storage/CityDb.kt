package com.example.weathertask.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weathertask.data.storage.dao.CityDao
import com.example.weathertask.data.storage.entities.CityModel

@Database(
    entities =[CityModel::class],
    version = 2
)

abstract class CityDb : RoomDatabase() {

    abstract fun cityDao(): CityDao

}