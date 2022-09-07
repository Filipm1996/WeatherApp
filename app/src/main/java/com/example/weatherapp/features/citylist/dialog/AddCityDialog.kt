package com.example.weatherapp.features.citylist.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.databinding.FragmentAddCityBinding
import com.example.weatherapp.domain.model.City
import com.example.weatherapp.features.citylist.dialog.adapter.AddCityAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCityDialog : DialogFragment() {

    private lateinit var binding: FragmentAddCityBinding
    private lateinit var adapter: AddCityAdapter
    private lateinit var onItemClick: ((City) -> Unit)

    private var listOfCities = mutableListOf<City>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAddCityBinding.inflate(layoutInflater)
        hideCityTitle()
        setUpRecyclerView()
        setUpClickListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setUpRecyclerView() {
        adapter = AddCityAdapter()
        binding.addCityRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.addCityRecyclerView.adapter = adapter
        adapter.updateListOfCities(listOfCities)
    }

    private fun setUpClickListeners() {
        adapter.onItemClickListener { city ->
            onItemClick.invoke(city)
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    fun updateListOfCities(list: List<City>) {
        this.listOfCities = list.toMutableList()
    }

    private fun hideCityTitle() {
        if (listOfCities.isEmpty()) {
            binding.addCityTitle.visibility = View.GONE
        }
    }

    fun onItemClickListener(callback: (City) -> Unit) {
        this.onItemClick = callback
    }
}