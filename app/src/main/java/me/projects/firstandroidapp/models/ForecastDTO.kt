package me.projects.firstandroidapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForecastDTO(
    @SerializedName("location") val location: Location,
    @SerializedName("current") val current: CurrentWeather,
    @SerializedName("forecast") val forecast: ForecastDays
): Serializable

data class ForecastDays(
    @SerializedName("forecastday") val forecastday: List<ForecastDay>
): Serializable