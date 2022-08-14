package com.example.weathertask.arch.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertask.common.Resource
import com.example.weathertask.databinding.ShowCitiesFragmentBinding
import com.example.weathertask.domain.model.Weather
import com.example.weathertask.features.adapter.MainAdapter
import com.example.weathertask.features.viewmodel.WeatherViewModel
import com.example.weathertask.utils.FlowExtensions.Companion.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowCitiesFragment : Fragment() {
    private val adapter = MainAdapter()
    private var someItemClickListener: OnItemClickListener? = null
    private val addAlertDialog = AddCityFragmentDialog()
    private lateinit var binding: ShowCitiesFragmentBinding
    private val viewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShowCitiesFragmentBinding.inflate(layoutInflater)
        someItemClickListener = activity as OnItemClickListener
        getErrorCollector()
        getLoadingCollector()
        setUpRecyclerView()
        setUpClickListeners()
    }

    private fun getLoadingCollector() {
        viewModel.getLoadingCollector().observe(this) {
            if (it == true) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeActions()
        return binding.root
    }

    interface OnItemClickListener {
        fun itemClickced(weather: Weather)
    }

    private fun getErrorCollector() {
        viewModel.getErrorCollector().observe(this) {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                viewModel.clearErrorCollector()
            }
        }
    }

    private fun setUpClickListeners() {
        adapter.itemClickListener {
            someItemClickListener!!.itemClickced(it)
        }
        binding.searchButton.setOnClickListener {
            val searchText = binding.searchText.text.toString()
            if (searchText.isNotEmpty()) {
                viewModel.getCoordinates(searchText)
                binding.searchText.text.clear()
            } else {
                Toast.makeText(requireContext(), "Please write city name", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        addAlertDialog.onItemClickListener {
            viewModel.insertCity(it)
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewForCities.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewForCities.adapter = adapter
        viewModel.getCities()
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                v: RecyclerView,
                h: RecyclerView.ViewHolder,
                t: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) {
                adapter.removeAt(h.adapterPosition) { name ->
                    viewModel.deleteCity(name)
                }
            }
        }).attachToRecyclerView(binding.recyclerViewForCities)
    }

    private fun observeActions() {
        viewModel.actions.observeWithLifecycle(viewLifecycleOwner) { action ->
            when (action) {
                is WeatherViewModel.Action.GetCitiesFromApi -> {
                    val list = action.listOfCities
                    addAlertDialog.show(parentFragmentManager, "add dialog")
                    addAlertDialog.updateListOfCities(list)
                }
                is WeatherViewModel.Action.GetWeatherForCities -> {
                    val list = action.listOfWeather
                    adapter.updateList(list)
                }
                is WeatherViewModel.Action.GetCitiesFromDb -> {
                    val cities = action.listOfCities
                    viewModel.getWeatherForCities(cities)
                }
                is WeatherViewModel.Action.InsertCity -> {
                    when (action.resource) {
                        is Resource.Error -> Toast.makeText(
                            requireContext(),
                            action.resource.message,
                            Toast.LENGTH_LONG
                        )
                            .show()
                        is Resource.Success -> Toast.makeText(
                            requireContext(),
                            action.resource.data,
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
                is WeatherViewModel.Action.GetWeatherForCity -> {
                    adapter.addToList(action.weather)
                }
            }
        }
    }
}