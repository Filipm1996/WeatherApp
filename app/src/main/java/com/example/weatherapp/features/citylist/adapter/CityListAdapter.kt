package com.example.weatherapp.features.citylist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.CityWeatherItemBinding
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.utils.Constants
import com.squareup.picasso.Picasso

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
        Picasso.get()
            .load("${Constants.WEATHER_HOST_URL}${element?.icon}@2x.png")
            .fit()
            .centerCrop()
            .into(holder.icon)
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
        val icon : ImageView = viewBinding.icon
        val cityName: TextView = viewBinding.cityName
        val temperature: TextView = viewBinding.cityWeatherTemperature
    }
}
