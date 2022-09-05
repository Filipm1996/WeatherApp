package com.example.weatherapp.features.hourlydetails.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.OneDayWeatherItemBinding
import com.example.weatherapp.domain.model.HourlyWeather
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class AdapterForWeather : RecyclerView.Adapter<AdapterForWeather.WeatherViewHolder>() {

    private var listOfWeather = listOf<HourlyWeather>()
    private lateinit var onHourClicked : (HourlyWeather)->Unit
    inner class WeatherViewHolder(
        viewBinding: OneDayWeatherItemBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        val hour = viewBinding.hour
        val temperature = viewBinding.temperatureForHour
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterForWeather.WeatherViewHolder {
        val viewBinding = OneDayWeatherItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeatherViewHolder(viewBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = listOfWeather[position]
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val hourToShow = LocalDateTime.parse(item.timestamp, formatter).hour
        holder.hour.text = "$hourToShow:00"
        holder.temperature.text = "${item.temp} \u2103"
        holder.itemView.setOnClickListener {
            onHourClicked.invoke(item)
        }
    }

    override fun getItemCount(): Int = listOfWeather.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(listOfWeather: List<HourlyWeather>) {
        this.listOfWeather = listOfWeather
        notifyDataSetChanged()
    }

    fun setOnHourClickListener(callback : (HourlyWeather)->Unit) {
        this.onHourClicked = callback
    }
}