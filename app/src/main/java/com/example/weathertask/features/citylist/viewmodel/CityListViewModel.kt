package com.example.weathertask.features.citylist.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertask.R
import com.example.weathertask.common.Resource
import com.example.weathertask.domain.DeleteCityUseCase
import com.example.weathertask.domain.GetCitiesUseCase
import com.example.weathertask.domain.GetWeatherForCitiesUseCase
import com.example.weathertask.domain.GetWeatherForCityUseCase
import com.example.weathertask.domain.InsertCityUseCase
import com.example.weathertask.domain.model.City
import com.example.weathertask.domain.model.Weather
import com.example.weathertask.utils.orErrorText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getWeatherForCityUseCase: GetWeatherForCityUseCase,
    private val getWeatherForCitiesUseCase: GetWeatherForCitiesUseCase,
    private val deleteCityUseCase: DeleteCityUseCase,
    private val insertCityUseCase: InsertCityUseCase
) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    fun getCities(cityName: String) = viewModelScope.launch {
        getCitiesUseCase.run(cityName).let { response ->
            when (response) {
                is Resource.Success -> response.data.let {
                    Action.ShowCityPickerDialog(it).send()
                }
                is Resource.Error ->
                    Action.ShowError(response.message.orErrorText()).send()
            }
        }
    }

    fun getCitiesWithWeather() = viewModelScope.launch {
        Action.ShowLoading.send()
        when (val response = getWeatherForCitiesUseCase.run(Unit)) {
            is Resource.Success -> Action.ShowCitiesWithWeather(response.data).send()
            is Resource.Error ->
                Action.ShowError(response.message.orErrorText()).send()
        }
        Action.HideLoading.send()
    }

    fun deleteCity(cityName: String) = viewModelScope.launch {
        deleteCityUseCase.run(cityName)
    }

    fun insertCity(city: City) = viewModelScope.launch {
        if (insertCityUseCase.run(city)) {
            Action.ShowSuccess(Resource.Success("Success")).send()
            getWeatherForCity(city)
        } else {
            Action.ShowErrorRes(R.string.city_already_added_error).send()
        }
    }

    private fun getWeatherForCity(city: City) = viewModelScope.launch {
        getWeatherForCityUseCase.run(
            GetWeatherForCityUseCase.Input(city.lat, city.lon, city.name)
        ).let { response ->
            when (response) {
                is Resource.Success -> {
                    val weather = response.data
                    Action.ShowWeatherForCity(weather).send()
                }
                is Resource.Error -> {
                    Action.ShowError(response.message.orErrorText()).send()
                }
            }
        }
    }

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }


    sealed class Action {
        data class ShowCityPickerDialog(val listOfCities: List<City>) : Action()
        data class ShowCitiesWithWeather(val listOfWeather: List<Weather>) : Action()
        data class ShowWeatherForCity(val weather: Weather) : Action()
        data class ShowSuccess(val resource: Resource<String>) : Action()
        data class ShowError(val error: String) : Action()
        data class ShowErrorRes(@StringRes val errorStringRes: Int) : Action()
        object ShowLoading : Action()
        object HideLoading : Action()
    }
}