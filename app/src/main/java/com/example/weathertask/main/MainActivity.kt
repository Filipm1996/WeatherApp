package com.example.weathertask.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weathertask.R
import com.example.weathertask.databinding.ActivityMainBinding
import com.example.weathertask.features.citylist.CityListFragment
import com.example.weathertask.features.citylist.CityListFragment.Companion.CITY_LIST_FRAGMENT_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showCityListFragment()
    }

    private fun showCityListFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_layout, CityListFragment.createInstance())
        ft.addToBackStack(CITY_LIST_FRAGMENT_TAG)
        ft.commit()
    }
}