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
        viewBinding.temperatureFellValue.text =
            resources.getString(R.string.temperature_value, weather.tempFell.toString())
        viewBinding.cityWeatherTemperature.text =
            resources.getString(R.string.temperature_value, weather.temp.toString())
        viewBinding.pressureValue.text =
            weather.pressure.toString() + resources.getString(R.string.hectopascals)
        viewBinding.visibilityValue.text =
            weather.visibility.toString() + resources.getString(R.string.meters)
        viewBinding.windSpeedValue.text =
            weather.windSpeed.toString() + resources.getString(R.string.speed)
        viewBinding.cloudinessValue.text =
            weather.cloudiness.toString() + resources.getString(R.string.percent)
        viewBinding.humidityValue.text =
            weather.humidity.toString() + resources.getString(R.string.percent)
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