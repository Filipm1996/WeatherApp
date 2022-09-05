package com.example.weatherapp.features.citylist.dialog.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.domain.model.City

class AddCityAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onCityClick: ((City) -> Unit)? = null
    private var listOfCities = mutableListOf<City>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AddCityViewType.CITY.ordinal -> AddCityViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.city_item, parent, false)
            )
            AddCityViewType.EMPTY.ordinal -> EmptyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.city_empty_item, parent, false)
            )
            else -> throw IllegalStateException("Unsupported view type")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? AddCityViewHolder)?.let { viewHolder ->
            val city = listOfCities[position]
            viewHolder.cityName.text = city.name
            viewHolder.country.text = city.country
            viewHolder.itemView.setOnClickListener {
                onCityClick?.invoke(city)
                listOfCities.remove(city)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int =
        if (listOfCities.size == 0) {
            1
        } else {
            listOfCities.size
        }

    override fun getItemViewType(position: Int): Int {
        return if (listOfCities.size == 0) {
            AddCityViewType.EMPTY.ordinal
        } else {
            AddCityViewType.CITY.ordinal
        }
    }

    fun updateListOfCities(list: List<City>) {
        this.listOfCities = list.toMutableList()
    }

    fun onItemClickListener(callback: (City) -> Unit) {
        this.onCityClick = callback
    }

    inner class AddCityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.findViewById(R.id.city_name)
        val country: TextView = itemView.findViewById(R.id.country)
    }

    inner class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private enum class AddCityViewType {
        CITY, EMPTY
    }
}