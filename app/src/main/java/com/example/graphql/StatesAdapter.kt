package com.example.graphql
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StatesAdapter : RecyclerView.Adapter<StatesAdapter.ViewHolder>() {
    private var states: List<State> = listOf()
    private var onItemClickListener: ((State) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.state, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(states[position])
    }

    override fun getItemCount(): Int {
        return states.size
    }

    fun setData(states: List<State>) {
        this.states = states
        notifyDataSetChanged()
    }
    fun setOnItemClickListener(listener: (State) -> Unit) {
        onItemClickListener = listener
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(state: State) {
            itemView.findViewById<TextView>(R.id.stateNameTextView).text = state.name
            itemView.setOnClickListener {
                onItemClickListener?.invoke(state)
            }
        }
    }
}
