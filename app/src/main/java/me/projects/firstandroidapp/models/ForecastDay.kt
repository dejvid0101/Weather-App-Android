package me.projects.firstandroidapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForecastDay(
    @SerializedName("date") val date: String,
    @SerializedName("date_epoch") val dateEpoch: Long,
    @SerializedName("day") val day: DailyWeather,
    @SerializedName("hour") val hours: List<Hour>
): Serializable