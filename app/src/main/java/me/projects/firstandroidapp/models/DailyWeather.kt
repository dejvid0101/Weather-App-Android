package me.projects.firstandroidapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DailyWeather(
    @SerializedName("maxtemp_c") val tempC: Double,
    @SerializedName("maxtemp_f") val tempF: Double,
    @SerializedName("mintemp_c") val mintempC: Double,
    @SerializedName("mintemp_f") val mintempF: Double,
    @SerializedName("avgtemp_c") val avgtempC: Double,
    @SerializedName("avgtemp_f") val avgtempF: Double,
    @SerializedName("maxwind_mph") val maxwindMph: Double,
    @SerializedName("maxwind_kph") val maxwindKph: Double,
    @SerializedName("totalprecip_mm") val totalprecipMm: Double,
    @SerializedName("totalprecip_in") val totalprecipIn: Double,
    @SerializedName("totalsnow_cm") val totalsnowCm: Double,
    @SerializedName("avgvis_km") val avgvisKm: Double,
    @SerializedName("avgvis_miles") val avgvisMiles: Double,
    @SerializedName("avghumidity") val humidity: Int,
    @SerializedName("daily_will_it_rain") val dailyWillItRain: Int,
    @SerializedName("daily_chance_of_rain") val dailyChanceOfRain: Int,
    @SerializedName("daily_will_it_snow") val dailyWillItSnow: Int,
    @SerializedName("daily_chance_of_snow") val dailyChanceOfSnow: Int,
    @SerializedName("condition") val condition: Condition,
    @SerializedName("uv") val uv: Double,
    @SerializedName("hour") val hours: List<Hour>
): Serializable