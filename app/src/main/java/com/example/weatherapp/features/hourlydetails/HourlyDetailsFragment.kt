package com.example.weatherapp.features.hourlydetails

import android.os.Bundle
import android.widget.Adapter
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.arch.BaseFragment
import com.example.weatherapp.databinding.FragmentHourlyDetailsBinding
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.features.hourlydetails.adapter.AdapterForDays
import com.example.weatherapp.features.hourlydetails.adapter.AdapterForWeather
import com.example.weatherapp.features.hourlydetails.viewmodel.HourlyDetailsViewModel
import com.example.weatherapp.utils.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HourlyDetailsFragment : BaseFragment<FragmentHourlyDetailsBinding>() {

    private lateinit var adapterForDays : AdapterForDays
    private lateinit var adapterForWeather : AdapterForWeather
    private lateinit var weather: Weather
    private val viewModel: HourlyDetailsViewModel by viewModels()

    override fun constructViewBinding(): FragmentHourlyDetailsBinding {
        return FragmentHourlyDetailsBinding.inflate(layoutInflater)
    }

    override fun init(viewBinding: FragmentHourlyDetailsBinding) {
        setUpDaysRecyclerViews()
        observeActions()
        weather = requireArguments().getSerializable(WEATHER_KEY) as Weather
        viewBinding.cityName.text = weather.cityName
        viewModel.getHourlyWeatherForCity(weather.cityName, weather.lat, weather.lon)
    }

    private fun setUpDaysRecyclerViews() {
        adapterForDays = AdapterForDays()
        getViewBinding().recyclerForDays.layoutManager = LinearLayoutManager(requireContext() ,LinearLayoutManager.HORIZONTAL, false)
        getViewBinding().recyclerForDays.adapter = adapterForDays
        adapterForDays.setOnDayClickedListener {
            viewModel.getWeatherListForDay(it)
        }
        adapterForWeather = AdapterForWeather()
        getViewBinding().recyclerViewForHourlyWeather.layoutManager = LinearLayoutManager(requireContext())
        getViewBinding().recyclerViewForHourlyWeather.adapter = adapterForWeather
        adapterForWeather.setOnHourClickListener {

        }
    }

    private fun observeActions() {
        viewModel.actions.observeWithLifecycle(this) { action ->
            when (action) {
                is HourlyDetailsViewModel.Action.ShowError -> {
                    showShortToast(message = action.error)
                }
                is HourlyDetailsViewModel.Action.ShowListOfHourlyWeather -> {
                    adapterForDays.updateList(action.listOfHourlyWeather)
                }
                is HourlyDetailsViewModel.Action.ShowListOfWeatherForDay -> {
                    adapterForWeather.updateList(action.listOfWeatherForDay)
                }
            }
        }
    }

    companion object {
        private const val WEATHER_KEY = "WEATHER_KEY"

        fun createInstance(weather: Weather): HourlyDetailsFragment =
            HourlyDetailsFragment()
                .apply {
                    arguments = Bundle().apply {
                        this.putSerializable(
                            WEATHER_KEY,
                            weather
                        )
                    }
                }
    }
}