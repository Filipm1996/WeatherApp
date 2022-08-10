package com.example.weatkertask.activity_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatkertask.data.db.Weather
import com.example.weatkertask.databinding.ActivityCityWeatherBinding
import com.squareup.picasso.Picasso

class CityWeatherActivity : AppCompatActivity() {
    lateinit var binding : ActivityCityWeatherBinding
    private lateinit var weather : Weather
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityWeatherBinding.inflate(layoutInflater)
        weather = intent.getSerializableExtra("weather") as Weather
        setText()
        setContentView(binding.root)
    }

    private fun setText() {
        binding.cityTitle.text = weather.cityName
        Picasso.get().load("https://openweathermap.org/img/wn/${weather.icon}@2x.png").into(binding.weatherIcon)
        binding.weatherDescription.text = weather.description
        binding.temperatureFellValue.text = weather.tempFell.toString() +" \u2103"
        binding.temperature.text = weather.temp.toString() +" \u2103"
        binding.presureValue.text = weather.pressure.toString() + " hPa"
        binding.visibilityValue.text = weather.visibility.toString() + " m"
        binding.windSpeedValue.text = weather.windSpeed.toString() +" m/s"
        binding.cloudinessValue.text = weather.cloudiness.toString() + " %"
        binding.humidityValue.text = weather.humidity.toString() + " %"
    }
}