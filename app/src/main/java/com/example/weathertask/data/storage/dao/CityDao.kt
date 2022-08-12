package com.example.weathertask.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weathertask.data.storage.entities.CityModel

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(cityModel: CityModel)

    @Query("SELECT * FROM citymodel")
    suspend fun getAllCities(): List<CityModel>

    @Query("DELETE FROM citymodel WHERE name= :name")
    fun deleteCityByName (name : String)

    @Query("SELECT EXISTS (SELECT 1 FROM citymodel WHERE name = :cityName)")
    suspend fun exists(cityName: String): Boolean
}