package me.projects.firstandroidapp.models

import com.google.gson.annotations.SerializedName

data class ForecastDay(
    @SerializedName("date") val date: String,
    @SerializedName("date_epoch") val dateEpoch: Long,
    @SerializedName("day") val day: DailyWeather
)