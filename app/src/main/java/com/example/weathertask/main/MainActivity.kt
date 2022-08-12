package com.example.weathertask.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weathertask.R
import com.example.weathertask.arch.base.CityWeatherFragment
import com.example.weathertask.arch.base.ShowCitiesFragment
import com.example.weathertask.databinding.ActivityMainBinding
import com.example.weathertask.domain.model.Weather
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ShowCitiesFragment.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private val showCitiesFragment = ShowCitiesFragment()
    private val cityWeatherFragment = CityWeatherFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpFragment(showCitiesFragment)
    }

    private fun setUpFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_layout, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun itemClickced(weather: Weather) {
        val bundle = Bundle()
        bundle.putSerializable("weather", weather)
        cityWeatherFragment.arguments = bundle
        setUpFragment(cityWeatherFragment)
    }

}