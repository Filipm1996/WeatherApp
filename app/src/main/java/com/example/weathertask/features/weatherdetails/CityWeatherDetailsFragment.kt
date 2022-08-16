package com.example.weathertask.features.weatherdetails

import android.os.Bundle
import com.example.weathertask.R
import com.example.weathertask.arch.BaseFragment
import com.example.weathertask.databinding.FragmentCityWeatherDetailsBinding
import com.example.weathertask.domain.model.Weather
import com.squareup.picasso.Picasso

class CityWeatherDetailsFragment : BaseFragment<FragmentCityWeatherDetailsBinding>() {

    override fun constructViewBinding(): FragmentCityWeatherDetailsBinding =
        FragmentCityWeatherDetailsBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentCityWeatherDetailsBinding) {
        val weather = requireArguments().getSerializable(WEATHER_KEY) as Weather
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
            resources.getString(R.string.hectopascals,weather.pressure.toString() )
        viewBinding.visibilityValue.text =
            resources.getString(R.string.meters,weather.visibility.toString())
        viewBinding.windSpeedValue.text =
            resources.getString(R.string.meters_per_second,weather.windSpeed.toString())
        viewBinding.cloudinessValue.text =
            resources.getString(R.string.percent,weather.cloudiness.toString())
        viewBinding.humidityValue.text =
            resources.getString(R.string.percent,weather.humidity.toString())
    }

    companion object {

        private const val WEATHER_HOST_URL = "https://openweathermap.org/img/wn/"
        private const val WEATHER_KEY = "WEATHER_KEY"

        fun createInstance(weather: Weather): CityWeatherDetailsFragment =
            CityWeatherDetailsFragment()
                .apply {
                    arguments = Bundle().apply { this.putSerializable(WEATHER_KEY, weather) }
                }
    }
}