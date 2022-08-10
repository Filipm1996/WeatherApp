package com.example.weatkertask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatkertask.common.Resource
import com.example.weatkertask.data.db.City
import com.example.weatkertask.data.db.Weather
import com.example.weatkertask.data.repositories.RepositoryDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: RepositoryDefault
) : ViewModel() {
    private lateinit var citiesLiveData: LiveData<List<City>>
    private val weatherLiveData = MutableLiveData<List<Weather>>()
    private val errorCollector = MutableLiveData<String?>()

    fun getCoordinates(cityName: String, callback: (List<City>?) -> Unit) {
        viewModelScope.launch {
            repository.getCoordinates(cityName).let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { callback.invoke(it) } ?: callback.invoke(null)
                    }
                    is Resource.Error -> {
                        errorCollector.postValue(response.message ?: "Error")
                    }
                }
            }
        }
    }

    fun getWeatherForCities(listOfCities: List<City>): MutableLiveData<List<Weather>> {
        viewModelScope.launch {
            val listOfShortWeather = mutableListOf<Weather>()
            listOfCities.forEach { city ->
                repository.getWeatherForCity(city.lat, city.lon, city.name).let { response ->
                    when (response) {
                        is Resource.Success -> {
                            val weather = response.data!!
                                listOfShortWeather.add(weather)

                        }
                        is Resource.Error -> {
                            errorCollector.postValue(response.message ?: "Error")
                        }
                    }

                }
            }
            weatherLiveData.postValue(listOfShortWeather)
        }
        return weatherLiveData
    }

    fun getCities(): LiveData<List<City>> {
        citiesLiveData = repository.getCities()
        return citiesLiveData
    }

    fun deleteCity(cityName: String) {
        repository.deleteCity(cityName)
    }

    fun insertCity(city: City ,callback: (Resource<String>) -> Unit){
        viewModelScope.launch {
            if (!repository.ifCityExists(city.name)) {
                repository.insertCity(city)
                callback.invoke(Resource.Success("Success"))
            } else {
                callback.invoke(Resource.Error("City is already added"))
            }
        }
    }


    fun getErrorCollector(): MutableLiveData<String?> {
        return errorCollector
    }

    fun clearErrorCollector() {
        errorCollector.value = null
    }

}