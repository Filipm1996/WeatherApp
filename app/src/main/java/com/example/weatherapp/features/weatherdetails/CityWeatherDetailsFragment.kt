package com.example.weatherapp.features.weatherdetails

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.arch.BaseFragment
import com.example.weatherapp.databinding.FragmentCityWeatherDetailsBinding
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.features.hourlydetails.HourlyDetailsFragment
import com.example.weatherapp.features.weatherdetails.dialog.PolutionDetailsFragment
import com.example.weatherapp.features.weatherdetails.viewmodel.CityWeatherDetailsViewModel
import com.example.weatherapp.utils.Constants.WEATHER_HOST_URL
import com.example.weatherapp.utils.observeWithLifecycle
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityWeatherDetailsFragment : BaseFragment<FragmentCityWeatherDetailsBinding>() {
    private lateinit var dialog : PolutionDetailsFragment
    private val viewModel: CityWeatherDetailsViewModel by viewModels()
    private lateinit var weather: Weather
    override fun constructViewBinding(): FragmentCityWeatherDetailsBinding =
        FragmentCityWeatherDetailsBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentCityWeatherDetailsBinding) {
        weather = requireArguments().getSerializable(WEATHER_KEY) as Weather
        viewBinding.cityWeatherTitle.text = weather.cityName
        Picasso.get()
            .load("$WEATHER_HOST_URL${weather.icon}@2x.png")
            .fit()
            .centerCrop()
            .into(viewBinding.cityWeatherIcon)
        viewBinding.weatherDescription.text = weather.description
        viewBinding.temperatureFeelLikeValue.text =
            resources.getString(R.string.temperature_value, weather.tempFell.toString())
        viewBinding.cityWeatherTemperature.text =
            resources.getString(R.string.temperature_value, weather.temp.toString())
        viewBinding.pressureValue.text =
            resources.getString(R.string.hectopascals, weather.pressure.toString())
        viewBinding.visibilityValue.text =
            resources.getString(R.string.meters, weather.visibility.toString())
        viewBinding.windSpeedValue.text =
            resources.getString(R.string.meters_per_second, weather.windSpeed.toString())
        viewBinding.cloudinessValue.text =
            resources.getString(R.string.percent, weather.cloudiness.toString())
        viewBinding.humidityValue.text =
            resources.getString(R.string.percent, weather.humidity.toString())
        observeActions()
        setUpClickListener()
    }

    private fun observeActions() {
        viewModel.actions.observeWithLifecycle(this){action ->
            when(action) {
                is CityWeatherDetailsViewModel.Action.ShowPolutionDetails -> {
                    dialog.updatePolutionDetails(action.polutionDetails)
                }
                is CityWeatherDetailsViewModel.Action.ShowError -> {
                    showShortToast(message = action.error)
                }
            }
        }
    }

    private fun setUpClickListener() {
        getViewBinding().hourlyWeatherButton.setOnClickListener {
            navigate(
                HourlyDetailsFragment.createInstance(weather),
                HOURLY_DETAILS_FRAGMENT_TAG
            )
        }
        getViewBinding().polutionButton.setOnClickListener {
            dialog = PolutionDetailsFragment()
            viewModel.getPolutionDetails(weather.lat,weather.lon)
            dialog.show(parentFragmentManager, DIALOG_TAG)
        }

    }

    companion object {

        private const val HOURLY_DETAILS_FRAGMENT_TAG = "HOURLY_DETAILS_FRAGMENT_TAG"
        private const val DIALOG_TAG = "polution dialog"
        private const val WEATHER_KEY = "WEATHER_KEY"

        fun createInstance(weather: Weather): CityWeatherDetailsFragment =
            CityWeatherDetailsFragment()
                .apply {
                    arguments = Bundle().apply { this.putSerializable(WEATHER_KEY, weather) }
                }
    }

    enum class Forecast(val type: String) {
        Hourly("Hourly forecast"),
        Days16("16 days forecast")
    }
}