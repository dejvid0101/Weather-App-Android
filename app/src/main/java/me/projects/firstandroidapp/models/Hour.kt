package me.projects.firstandroidapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Hour(
    @SerializedName("time") val time: String,
    @SerializedName("condition") val condition: HourlyCondition,
    @SerializedName("temp_c") val tempC: Double
): Serializable

data class HourlyCondition(
    @SerializedName("text") val text: String,
    @SerializedName("icon") val icon: String
): Serializable