package com.example.weatkertask.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatkertask.R
import com.example.weatkertask.data.db.Weather


class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var onClickItem : ((Weather)->Unit)? = null
    private var listOfShortWeathers = mutableListOf<Weather>()
    inner class MainViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val cityName : TextView = itemView.findViewById(R.id.cityName)
        val temperature : TextView = itemView.findViewById(R.id.temperature)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_element,parent,false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val element = listOfShortWeathers[position]
        holder.cityName.text = element.cityName
        holder.temperature.text = element.temp.toString() +" \u2103"
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(element)
        }
    }

    override fun getItemCount(): Int {
        return listOfShortWeathers.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList (list : List<Weather>){
        this.listOfShortWeathers = list.toMutableList()
        notifyDataSetChanged()
    }

    fun removeAt(index: Int, callback : (String) -> Unit) {
        callback.invoke(listOfShortWeathers[index].cityName)
        listOfShortWeathers.removeAt(index)
        notifyItemRemoved(index)
    }

    fun itemClickListener(callback: (Weather) -> Unit){
        this.onClickItem = callback
    }
}

