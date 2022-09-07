package com.example.weatherapp.features.weatherdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.GetPolutionDetailsUseCase
import com.example.weatherapp.domain.model.HourlyWeather
import com.example.weatherapp.domain.model.PolutionDetails
import com.example.weatherapp.features.hourlydetails.viewmodel.HourlyDetailsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CityWeatherDetailsViewModel@Inject constructor(
    private val getPolutionDetailsUseCase: GetPolutionDetailsUseCase
) : ViewModel() {

    private val _actions = Channel<HourlyDetailsViewModel.Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    fun getPolutionDetails (lat:Double,lon:Double)= viewModelScope.launch{
        val now = LocalDateTime.now()
        when(val result = getPolutionDetailsUseCase.run(listOf(lat,lon,now,now))){
            is Resource.Success -> Action.ShowPolutionDetails(result.data)
            is Resource.Error -> Action.ShowError(result.message)
        }
    }

    sealed class Action {
        data class ShowPolutionDetails(val polutionDetails: PolutionDetails) : Action()
        data class ShowError(val error: String) : Action()
    }
}