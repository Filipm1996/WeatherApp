package com.example.weatherapp.features.citylist

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.arch.BaseFragment
import com.example.weatherapp.common.Resource
import com.example.weatherapp.databinding.FragmentCityListBinding
import com.example.weatherapp.domain.model.City
import com.example.weatherapp.features.citylist.adapter.CityListAdapter
import com.example.weatherapp.features.citylist.dialog.AddCityDialog
import com.example.weatherapp.features.citylist.viewmodel.CityListViewModel
import com.example.weatherapp.features.weatherdetails.CityWeatherDetailsFragment
import com.example.weatherapp.utils.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityListFragment : BaseFragment<FragmentCityListBinding>() {

    private val viewModel: CityListViewModel by viewModels()

    private lateinit var addAlertDialog: AddCityDialog
    private lateinit var adapter: CityListAdapter

    override fun constructViewBinding() =
        FragmentCityListBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentCityListBinding) {
        setUpRecyclerView()
        setUpClickListeners()
        observeActions()
        viewModel.getCitiesWithWeather()
    }

    private fun setUpClickListeners() {
        adapter.itemClickListener {
            navigate(
                CityWeatherDetailsFragment.createInstance(it),
                CITY_WEATHER_DETAILS_FRAGMENT_TAG
            )
        }
        getViewBinding().searchButton.setOnClickListener {
            val searchText = getViewBinding().searchText.text.toString()
            if (searchText.isNotBlank()) {
                viewModel.getCities(searchText)
                getViewBinding().searchText.text.clear()
            } else {
                showShortToast(messageRes = R.string.write_city_name)
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = CityListAdapter()
        getViewBinding().recyclerViewForCities.layoutManager = LinearLayoutManager(requireContext())
        getViewBinding().recyclerViewForCities.adapter = adapter
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
        }).attachToRecyclerView(getViewBinding().recyclerViewForCities)
    }

    private fun observeActions() {
        viewModel.actions.observeWithLifecycle(viewLifecycleOwner) { action ->
            when (action) {
                is CityListViewModel.Action.ShowCityPickerDialog ->
                    showDialog(action.listOfCities)
                is CityListViewModel.Action.ShowCitiesWithWeather ->
                    adapter.updateList(action.listOfWeather)
                is CityListViewModel.Action.ShowSuccess ->
                    showAddCitySuccess(action.resource)
                is CityListViewModel.Action.ShowWeatherForCity ->
                    adapter.addToList(action.weather)
                CityListViewModel.Action.ShowLoading ->
                    getViewBinding().progressBar.isVisible = true
                CityListViewModel.Action.HideLoading ->
                    getViewBinding().progressBar.isVisible = false
                is CityListViewModel.Action.ShowError ->
                    Toast.makeText(requireContext(), action.error, Toast.LENGTH_LONG).show()
                is CityListViewModel.Action.ShowErrorRes ->
                    showShortToast(messageRes = action.errorStringRes)
            }
        }
    }

    private fun showDialog(list: List<City>) {
        addAlertDialog = AddCityDialog()
        addAlertDialog.show(parentFragmentManager, ADD_CITY_DIALOG_TAG)
        addAlertDialog.updateListOfCities(list)
        addAlertDialog.onItemClickListener {
            viewModel.insertCity(it)
        }
    }

    private fun showAddCitySuccess(result: Resource<String>) {
        when (result) {
            is Resource.Error -> showShortToast(message = result.message)
            is Resource.Success -> showShortToast(message = result.data)
        }
    }

    companion object {

        private const val ADD_CITY_DIALOG_TAG = "ADD_CITY_DIALOG_TAG"
        private const val CITY_WEATHER_DETAILS_FRAGMENT_TAG = "CITY_WEATHER_DETAILS_FRAGMENT_TAG"

        const val CITY_LIST_FRAGMENT_TAG = "CITY_LIST_FRAGMENT_TAG"

        fun createInstance() = CityListFragment()
    }
}