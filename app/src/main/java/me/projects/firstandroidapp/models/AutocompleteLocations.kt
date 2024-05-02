package me.projects.firstandroidapp.models

import com.google.gson.annotations.SerializedName

data class AutocompleteLocations (
    val locations: List<AutocompleteLocation>
)

data class AutocompleteLocation(
    @SerializedName("country") val country: String,
    @SerializedName("geonameid") val geonameid: Int,
    @SerializedName("name") val name: String,
    @SerializedName("subcountry") val subcountry: String
)