package com.example.graphql

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {
    private var cities: List<City> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun setData(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }
    fun updateData(newCities: List<City>) {
        cities = newCities
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: City) {
            itemView.findViewById<TextView>(R.id.cityNameTextView).text = city.name
        }
    }
}