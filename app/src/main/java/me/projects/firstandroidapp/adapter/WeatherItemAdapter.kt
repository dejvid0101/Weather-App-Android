package me.projects.firstandroidapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import me.projects.firstandroidapp.DayForecastActivity
import me.projects.firstandroidapp.R
import me.projects.firstandroidapp.interfaces.OnItemClickListener


class WeatherItemAdapter(private val dataList: List<String>, private val listener: OnItemClickListener) : RecyclerView.Adapter<WeatherItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val textView: TextView = itemView.findViewById(R.id.text_current_temperature)
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(item: String) {
            textView.text = item
        }

        override fun onClick(p0: View?) {
            listener.onItemClick(textView.text.toString())
        }
    }
}
