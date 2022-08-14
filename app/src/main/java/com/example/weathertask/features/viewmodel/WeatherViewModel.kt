package com.example.weathertask.features.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertask.arch.usecase.UseCaseDefault
import com.example.weathertask.common.Resource
import com.example.weathertask.domain.model.City
import com.example.weathertask.domain.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val useCase: UseCaseDefault
) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()
    private val errorCollector = MutableLiveData<String?>()
    private val loadingCollector = MutableLiveData<Boolean>()

    fun getCoordinates(cityName: String) {
        viewModelScope.launch {
            useCase.getCoordinates(cityName).let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            _actions.send(Action.GetCitiesFromApi(it))
                        }
                    }
                    is Resource.Error -> {
                        errorCollector.postValue(response.message ?: "Error")
                    }
                }
            }
        }
    }

    fun getWeatherForCities(listOfCities: List<City>) {
        loadingCollector.postValue(true)
        viewModelScope.launch {
            val listOfWeather = mutableListOf<Weather>()
            listOfCities.forEach { city ->
                useCase.getWeatherForCity(city.lat, city.lon, city.name).let { response ->
                    when (response) {
                        is Resource.Success -> {
                            val weather = response.data!!
                            listOfWeather.add(weather)
                        }
                        is Resource.Error -> {
                            errorCollector.postValue(response.message ?: "Error")
                        }
                    }
                }
            }
            loadingCollector.postValue(false)
            if (listOfCities.isNotEmpty()) {
                _actions.send(Action.GetWeatherForCities(listOfWeather))
            }
        }
    }

    private fun getWeatherForCity(city: City) {
        viewModelScope.launch {
            useCase.getWeatherForCity(city.lat, city.lon, city.name).let { response ->
                when (response) {
                    is Resource.Success -> {
                        val weather = response.data!!
                        _actions.send(Action.GetWeatherForCity(weather))
                    }
                    is Resource.Error -> {
                        errorCollector.postValue(response.message ?: "Error")
                    }
                }
            }
        }
    }

    fun getCities() {
        viewModelScope.launch {
            _actions.send(Action.GetCitiesFromDb(useCase.getCities()))
        }
    }

    fun deleteCity(cityName: String) {
        useCase.deleteCity(cityName)
    }

    fun insertCity(city: City) {
        viewModelScope.launch {
            if (!useCase.ifCityExists(city.name)) {
                useCase.insertCity(city)
                _actions.send(Action.InsertCity(Resource.Success("Success")))
                getWeatherForCity(city)
            } else {
                _actions.send(Action.InsertCity(Resource.Error("City is already added")))
            }
        }
    }

    fun getErrorCollector(): MutableLiveData<String?> {
        return errorCollector
    }

    fun clearErrorCollector() {
        errorCollector.value = null
    }

    fun getLoadingCollector(): MutableLiveData<Boolean> {
        return loadingCollector
    }

    sealed class Action {
        data class GetCitiesFromApi(val listOfCities: List<City>) : Action()
        data class GetWeatherForCities(val listOfWeather: List<Weather>) : Action()
        data class GetWeatherForCity(val weather: Weather) : Action()
        data class GetCitiesFromDb(val listOfCities: List<City>) : Action()
        data class InsertCity(val resource: Resource<String>) : Action()
    }

}