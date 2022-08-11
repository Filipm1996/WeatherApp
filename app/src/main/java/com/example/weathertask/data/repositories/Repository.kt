package com.example.weathertask.data.repositories

import com.example.weathertask.common.Constants
import com.example.weathertask.common.Resource
import com.example.weathertask.data.Retrofit.api.ApiService
import com.example.weathertask.data.Retrofit.entities.Coordinates.CoordinatesResponse
import com.example.weathertask.data.Retrofit.entities.WeatherForCity.CurrentWeatherResponse
import com.example.weathertask.data.db.City
import com.example.weathertask.data.db.CityDao
import com.example.weathertask.data.db.Weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService, private val cityDao: CityDao) : RepositoryDefault {

    override suspend fun getCoordinates(cityName : String) : Resource<List<City>>{
        return try {
            val coordinatesResponse : CoordinatesResponse = CoroutineScope(Dispatchers.IO).async {
                apiService.getCoordinates(cityName)
            }.await()
            val listOfCities = mutableListOf<City>()
            coordinatesResponse.forEach{
                val city = City(it.lat, it.lon, it.name, it.country)
                listOfCities.add(city)
            }
            Resource.Success(listOfCities)
        } catch (e : Exception){
            Resource.Error(e.message ?: "Error")
        }
    }

    override suspend fun getWeatherForCity(lat : Double , lon : Double,cityName: String) : Resource<Weather>{
        return try {
            val currentWeatherResponse : CurrentWeatherResponse = CoroutineScope(Dispatchers.IO).async {
                apiService.getWeatherForCity(lat, lon)
            }.await()
            var weather : Weather
            currentWeatherResponse.let {
                weather = Weather(
                    cityName,
                    it.weather[0].description,
                    it.weather[0].icon,
                    it.visibility,
                    it.wind.speed,
                    it.clouds.all,
                    BigDecimal(it.main.temp).setScale(0, RoundingMode.HALF_DOWN).toInt(),
                    BigDecimal(it.main.feels_like).setScale(0, RoundingMode.HALF_DOWN).toInt(),
                    it.main.pressure,
                    it.main.humidity
                )
            }
            Resource.Success(weather)
        } catch (e : Exception){
            Resource.Error(e.message ?: "Error")
        }
    }

    override fun getCities() = cityDao.getAllCities()

    override suspend fun ifCityExists(cityName: String) = cityDao.exists(cityName)

    override fun deleteCity(cityName:String) {
        CoroutineScope(Dispatchers.IO).launch{
            cityDao.deleteCityByName(cityName)
        }
    }

    override fun insertCity(city: City) {
        CoroutineScope(Dispatchers.IO).launch { cityDao.insertCity(city) }
    }
}