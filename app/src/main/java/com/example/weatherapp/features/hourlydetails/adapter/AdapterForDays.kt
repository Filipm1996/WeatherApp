package com.example.weatherapp.features.hourlydetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.DaysItemBinding
import com.example.weatherapp.domain.model.HourlyWeather
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AdapterForDays : RecyclerView.Adapter<AdapterForDays.DaysViewHolder>() {

    private lateinit var dayClicked : ((LocalDateTime) -> Unit)
    var listOfDays = mutableListOf<LocalDateTime>()

    inner class DaysViewHolder(
        viewBinding: DaysItemBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        val button  = viewBinding.daysButton
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterForDays.DaysViewHolder {
        val viewBinding = DaysItemBinding.inflate(
                LayoutInflater.from(parent.context),
        parent,
        false
        )
        return DaysViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: AdapterForDays.DaysViewHolder, position: Int) {
        val dayToShow = listOfDays[position]
        holder.button.text = dayToShow.dayOfWeek.name
        holder.button.setOnClickListener {
            dayClicked.invoke(dayToShow)
        }
    }

    override fun getItemCount(): Int {
        return listOfDays.size
    }

    fun updateList(list : List<HourlyWeather>){
        val firstDayString = list.first().timestamp
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val firstDayDate = LocalDateTime.parse(firstDayString,formatter)
        listOfDays.add(firstDayDate)
        var dayToAdd = firstDayDate
        for(i in 1..4){
            dayToAdd = dayToAdd.plusDays(1)
            listOfDays.add(dayToAdd)
        }
        notifyDataSetChanged()
    }

    fun setOnDayClickedListener(callback : (LocalDateTime) -> Unit){
        this.dayClicked = callback
    }
}