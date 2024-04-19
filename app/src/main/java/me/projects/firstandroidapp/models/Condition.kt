package me.projects.firstandroidapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.io.SerializablePermission

data class Condition(
    @SerializedName("text") val text: String,
    @SerializedName("icon") val icon: String
): Serializable