package com.example.weathertask.features.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertask.R
import com.example.weathertask.domain.model.City

class AddCityAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onCityClick: ((City) -> Unit)? = null
    private var listOfCities = mutableListOf<City>()

    companion object {
        const val viewHolder = 1
        const val emptyViewHolder = 2
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.findViewById(R.id.cityName)
        val country: TextView = itemView.findViewById(R.id.country)
    }

    inner class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            MainViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_view_add_city_element, parent, false)
            )
        } else {
            EmptyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_view_add_city_empty, parent, false)
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainViewHolder -> {
                val city = listOfCities[position]
                holder.cityName.text = city.name
                holder.country.text = city.country
                holder.itemView.setOnClickListener {
                    onCityClick!!.invoke(city)
                    listOfCities.remove(city)
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (listOfCities.size == 0) {
            1
        } else {
            listOfCities.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listOfCities.size == 0) {
            emptyViewHolder
        } else {
            viewHolder
        }
    }

    fun updateListOfCities(list: List<City>) {
        this.listOfCities = list.toMutableList()
    }

    fun onItemClickListener(callback: (City) -> Unit) {
        this.onCityClick = callback
    }
}