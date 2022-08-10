package com.example.weathertask.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: City)

    @Query("SELECT * FROM city")
    fun getAllCities(): LiveData<List<City>>

    @Query("DELETE FROM city WHERE name= :name")
    fun deleteCityByName (name : String)

    @Query("SELECT EXISTS (SELECT 1 FROM city WHERE name = :cityName)")
    suspend fun exists(cityName: String): Boolean
}