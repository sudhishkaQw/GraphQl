package com.example.graphql
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CountriesAdapter : RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {
    private var countries: List<Country> = listOf()
    private var onItemClickListener: ((Country) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country)
    }

    override fun getItemCount(): Int = countries.size

    fun setData(countries: List<Country>) {
        this.countries = countries
        notifyDataSetChanged()
    }
    fun setOnItemClickListener(listener: (Country) -> Unit) {
        onItemClickListener = listener
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(country: Country) {
            itemView.findViewById<TextView>(R.id.textViewCountryName).text = country.name

            val imageViewFlag = itemView.findViewById<ImageView>(R.id.imageViewFlag)
            Glide.with(itemView)
                .load(country.flag)
                .placeholder(R.drawable.baseline_flag_24)
                .error(R.drawable.baseline_flag_24)
                .into(imageViewFlag)
            itemView.setOnClickListener {
                onItemClickListener?.invoke(country)
            }
        }
    }

}
