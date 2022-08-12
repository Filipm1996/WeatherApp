package com.example.weathertask.arch.base

import dagger.hilt.android.AndroidEntryPoint


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathertask.data.storage.entities.CityModel
import com.example.weathertask.databinding.AddCityFragmentBinding
import com.example.weathertask.domain.model.City
import com.example.weathertask.features.adapter.AddCityAdapter

@AndroidEntryPoint
class AddCityFragmentDialog : DialogFragment()  {
    private lateinit var binding: AddCityFragmentBinding
    private val adapter = AddCityAdapter()
    private var listOfCities = mutableListOf<City>()
    private var onItemClick : ((City)->Unit)? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddCityFragmentBinding.inflate(layoutInflater)
        setUpText()
        setUpRecyclerView()
        setUpClickListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private fun setUpClickListeners() {
        adapter.onItemClickListener { city->
            onItemClick!!.invoke(city)
            dismiss()
        }
    }

    private fun setUpRecyclerView() {
        binding.addCityRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.addCityRecyclerView.adapter = adapter
        adapter.updateListOfCities(listOfCities)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    fun updateListOfCities(list : List<City>){
        this.listOfCities = list.toMutableList()
    }

    private fun setUpText() {
        if(listOfCities.isEmpty()){
            println("here")
            binding.title.visibility = View.GONE
        }
    }

    fun onItemClickListener(callback : (City)->Unit ){
        this.onItemClick = callback
    }
}