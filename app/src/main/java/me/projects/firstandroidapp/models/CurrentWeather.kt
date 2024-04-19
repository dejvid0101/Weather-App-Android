package me.projects.firstandroidapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CurrentWeather(
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("temp_c") val tempC: Double,
    @SerializedName("temp_f") val tempF: Double,
    @SerializedName("is_day") val isDay: Int,
    @SerializedName("condition") val condition: Condition,
    @SerializedName("wind_kph") val windKph: Double,
    @SerializedName("wind_dir") val windDir: String,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("cloud") val cloud: Int,
    @SerializedName("feelslike_c") val feelslikeC: Double,
    @SerializedName("feelslike_f") val feelslikeF: Double
) : Serializable {
}

