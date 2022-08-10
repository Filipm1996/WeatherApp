package com.example.weathertask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities =[City::class],
    version = 1
)

abstract class CityDb : RoomDatabase() {

    abstract fun cityDao(): CityDao

}