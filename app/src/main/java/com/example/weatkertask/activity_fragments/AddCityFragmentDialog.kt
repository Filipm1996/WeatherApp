package com.example.weatkertask.activity_fragments

import dagger.hilt.android.AndroidEntryPoint


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatkertask.data.db.City
import com.example.weatkertask.databinding.AddCityFragmentBinding
import com.example.weatkertask.ui.AddCityAdapter
import com.example.weatkertask.ui.WeatherViewModel

@AndroidEntryPoint
class AddCityFragmentDialog : DialogFragment()  {
    private lateinit var binding: AddCityFragmentBinding
    private val adapter = AddCityAdapter()
    private var listOfCities = mutableListOf<City>()
    private var onItemClick : ((City)->Unit)? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddCityFragmentBinding.inflate(layoutInflater)
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

    fun onItemClickListener(callback : (City)->Unit ){
        this.onItemClick = callback
    }
}