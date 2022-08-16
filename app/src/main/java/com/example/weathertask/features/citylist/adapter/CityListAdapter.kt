package com.example.weathertask.features.citylist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertask.R
import com.example.weathertask.databinding.CityWeatherItemBinding
import com.example.weathertask.domain.model.Weather

class CityListAdapter : RecyclerView.Adapter<CityListAdapter.CityItemViewHolder>() {

    private lateinit var onClickItem: ((Weather) -> Unit)
    private var weatherList = mutableListOf<Weather>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemViewHolder {
        val viewBinding = CityWeatherItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityItemViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: CityItemViewHolder, position: Int) {
        val element = weatherList[position]
        holder.cityName.text = element.cityName
        holder.temperature.text = holder.itemView.context.resources
            .getString(R.string.temperature_value, element.temp.toString())
        holder.itemView.setOnClickListener {
            onClickItem.invoke(element)
        }
    }

    override fun getItemCount(): Int = weatherList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Weather>) {
        this.weatherList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun addToList(weather: Weather) {
        this.weatherList.add(weather)
        notifyItemRangeChanged(weatherList.size - 1, itemCount)
    }

    fun removeAt(index: Int, callback: (String) -> Unit) {
        callback.invoke(weatherList[index].cityName)
        weatherList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun itemClickListener(callback: (Weather) -> Unit) {
        this.onClickItem = callback
    }

    inner class CityItemViewHolder(
        viewBinding: CityWeatherItemBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        val cityName: TextView = viewBinding.cityName
        val temperature: TextView = viewBinding.cityWeatherTemperature
    }
}
