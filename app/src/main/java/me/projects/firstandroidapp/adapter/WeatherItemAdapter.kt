package me.projects.firstandroidapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import me.projects.firstandroidapp.DayForecastActivity
import me.projects.firstandroidapp.R
import me.projects.firstandroidapp.interfaces.OnItemClickListener
import me.projects.firstandroidapp.models.ForecastDTO
import com.bumptech.glide.Glide

class WeatherItemAdapter(private val forecast: ForecastDTO, private val listener: OnItemClickListener, private val isHourly: Boolean) : RecyclerView.Adapter<WeatherItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutId = if (isHourly) R.layout.weather_item else R.layout.weather_item_horizontal
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (isHourly) {
            24
        } else {
            forecast.forecast.forecastday.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)


        if(isHourly) {
            // load cdn icon to ImageView
            val imageUrl = "https://" + forecast.forecast.forecastday[0].hours[position].condition.icon
            Glide.with(holder.itemView)
                .load(imageUrl)
                .into(holder.imageView)
        } else {
            val imageUrl = "https://" + forecast.forecast.forecastday[position].day.condition.icon
            Glide.with(holder.itemView)
                .load(imageUrl)
                .into(holder.imageView)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val textViewTemp: TextView = itemView.findViewById(if (isHourly) R.id.text_current_temperature else R.id.text_current_temperature_horizontal)
        private val textViewTime: TextView = itemView.findViewById(R.id.banner_one)
        private val textViewCondition: TextView = itemView.findViewById(R.id.text_weather_condition)
        val imageView: ImageView = itemView.findViewById(R.id.image_weather_icon)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(position: Int) {



            if (isHourly) {
                val hour = forecast.forecast.forecastday[0].hours[adapterPosition]
                textViewTemp.text = hour.tempC.toString()
                textViewTime.text = hour.time
                textViewCondition.text = hour.condition.text
            } else {
                val forecastDay = forecast.forecast.forecastday[adapterPosition]
                textViewTemp.text = forecastDay.day.tempC.toString()
                textViewTime.text = forecastDay.date
                textViewCondition.text = forecastDay.day.condition.text
            }
        }

        override fun onClick(view: View) {
            listener.onItemClick(textViewTemp.text.toString())
        }
    }
}
