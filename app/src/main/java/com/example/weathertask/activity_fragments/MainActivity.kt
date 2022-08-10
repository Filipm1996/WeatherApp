package com.example.weathertask.activity_fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertask.common.Resource
import com.example.weathertask.databinding.ActivityMainBinding
import com.example.weathertask.ui.MainAdapter
import com.example.weathertask.ui.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val adapter = MainAdapter()
    private val addAlertDialog = AddCityFragmentDialog()
    private lateinit var binding: ActivityMainBinding
    private val viewModel : WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        getErrorCollector()
        setUpRecyclerView()
        setUpClickListeners()
        setContentView(binding.root)
    }

    private fun getErrorCollector() {
        viewModel.getErrorCollector().observe(this){
            if(it!=null){
                Toast.makeText(this,it,Toast.LENGTH_LONG).show()
                viewModel.clearErrorCollector()
            }
        }
    }

    private fun setUpClickListeners() {
        adapter.itemClickListener {
            val intent = Intent(this, CityWeatherActivity::class.java)
            intent.putExtra("weather",it)
            startActivity(intent)
        }
        binding.searchButton.setOnClickListener {
            val searchText = binding.searchText.text.toString()
            if(searchText.isNotEmpty()){
                viewModel.getCoordinates(searchText) {
                    if (it != null) {
                        addAlertDialog.updateListOfCities(it)
                    }
                    addAlertDialog.show(supportFragmentManager, "add dialog")
                }
                binding.searchText.text.clear()
            }else{
                Toast.makeText(this,"Please write city name", Toast.LENGTH_SHORT).show()
            }
        }
        addAlertDialog.onItemClickListener {
            viewModel.insertCity(it) { response->
                when (response) {
                    is Resource.Error -> Toast.makeText(this, response.message, Toast.LENGTH_LONG)
                        .show()
                    is Resource.Success -> Toast.makeText(this, response.data, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewForCities.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewForCities.adapter = adapter
        viewModel.getCities().observe(this){ listOfCities->
            viewModel.getWeatherForCities(listOfCities).observe(this){listOfWeather ->
                adapter.updateList(listOfWeather)
            }
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(v: RecyclerView, h: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) {
                adapter.removeAt(h.adapterPosition){ name->
                    viewModel.deleteCity(name)
                }
            }
        }).attachToRecyclerView(binding.recyclerViewForCities)

    }
}