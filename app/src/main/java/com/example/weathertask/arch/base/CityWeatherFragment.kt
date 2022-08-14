package com.example.weathertask.arch.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weathertask.databinding.CityWeatherFragmentBinding
import com.example.weathertask.domain.model.Weather
import com.squareup.picasso.Picasso

class CityWeatherFragment : Fragment() {
    private lateinit var binding: CityWeatherFragmentBinding
    private lateinit var weather: Weather
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CityWeatherFragmentBinding.inflate(layoutInflater)
        weather = requireArguments().get("weather") as Weather
        setText()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun setText() {
        binding.cityTitle.text = weather.cityName
        Picasso.get().load("https://openweathermap.org/img/wn/${weather.icon}@2x.png").fit()
            .centerCrop().into(binding.weatherIcon)
        binding.weatherDescription.text = weather.description
        binding.temperatureFellValue.text = weather.tempFell.toString() + " \u2103"
        binding.temperature.text = weather.temp.toString() + " \u2103"
        binding.pressureValue.text = weather.pressure.toString() + " hPa"
        binding.visibilityValue.text = weather.visibility.toString() + " m"
        binding.windSpeedValue.text = weather.windSpeed.toString() + " m/s"
        binding.cloudinessValue.text = weather.cloudiness.toString() + " %"
        binding.humidityValue.text = weather.humidity.toString() + " %"
    }

}