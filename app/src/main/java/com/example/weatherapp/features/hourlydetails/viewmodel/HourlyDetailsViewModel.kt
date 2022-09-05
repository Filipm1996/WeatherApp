package com.example.weatherapp.features.hourlydetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.GetHourlyWeatherUseCase
import com.example.weatherapp.domain.model.City
import com.example.weatherapp.domain.model.HourlyWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HourlyDetailsViewModel @Inject constructor(
    private val getHourlyWeatherUseCase: GetHourlyWeatherUseCase
) : ViewModel() {
    private lateinit var listOfWeather: List<HourlyWeather>
    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()
    fun getHourlyWeatherForCity(cityName: String, lat: Double, lon: Double) =
        viewModelScope.launch {
            when (val response = getHourlyWeatherUseCase.run(listOf(cityName, lat, lon))) {
                is Resource.Success -> {
                    listOfWeather = response.data
                    Action.ShowListOfHourlyWeather(response.data).send()
                }
                is Resource.Error -> Action.ShowError(response.message).send()
            }
        }

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    fun getWeatherListForDay(day: LocalDateTime) = viewModelScope.launch{
        val dayInt = day.dayOfWeek.value
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val listOfWeatherForDay = mutableListOf<HourlyWeather>()
        listOfWeather.forEach {
            val dayIntItem = LocalDateTime.parse(it.timestamp, formatter).dayOfWeek.value
            if(dayIntItem == dayInt){
                listOfWeatherForDay.add(it)
            }
        }
        Action.ShowListOfWeatherForDay(listOfWeatherForDay).send()
    }

    sealed class Action {
        data class ShowListOfHourlyWeather(val listOfHourlyWeather: List<HourlyWeather>) : Action()
        data class ShowError(val error: String) : Action()
        data class ShowListOfWeatherForDay(val listOfWeatherForDay : List<HourlyWeather>) : Action()
    }
}