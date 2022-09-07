package com.example.weatherapp.features.hourlydetails.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentEachHourDetailBinding
import com.example.weatherapp.domain.model.HourlyWeather
import com.example.weatherapp.utils.Constants.WEATHER_HOST_URL
import com.squareup.picasso.Picasso

class HourlyWeatherDialog : DialogFragment() {

    private var weather: HourlyWeather? = null
    private lateinit var binding: FragmentEachHourDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEachHourDetailBinding.inflate(layoutInflater)
        setUpText()
    }

    private fun setUpText() {
        Picasso.get()
            .load("${WEATHER_HOST_URL}${weather?.icon}@2x.png")
            .fit()
            .centerCrop()
            .into(binding.cityWeatherIcon)
        binding.temperatureFeelLikeValue.text =
            resources.getString(R.string.temperature_value, weather?.tempFell.toString())
        binding.cityWeatherTemperature.text =
            resources.getString(R.string.temperature_value, weather?.temp.toString())
        binding.pressureValue.text =
            resources.getString(R.string.hectopascals, weather?.pressure.toString())
        binding.visibilityValue.text =
            resources.getString(R.string.meters, weather?.visibility.toString())
        binding.windSpeedValue.text =
            resources.getString(R.string.meters_per_second, weather?.windSpeed.toString())
        binding.cloudinessValue.text =
            resources.getString(R.string.percent, weather?.cloudiness.toString())
        binding.humidityValue.text =
            resources.getString(R.string.percent, weather?.humidity.toString())
        binding.weatherDescription.text = weather?.description
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun updateWeather(weather: HourlyWeather) {
        this.weather = weather
    }
}