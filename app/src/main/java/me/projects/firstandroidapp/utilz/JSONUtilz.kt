package me.projects.firstandroidapp.utils

import com.google.gson.Gson
import me.projects.firstandroidapp.models.AutocompleteLocation
import java.io.InputStream

object JSONUtilz {

    fun loadAutocompleteCities(inputStream: InputStream): Array<AutocompleteLocation>? {
        val jsonContent = inputStream.bufferedReader().use { it.readText() }

        return if (jsonContent != null) {
            try {
                // Parse JSON data using Gson
                val gson = Gson()
                val cities = gson.fromJson(jsonContent, Array<AutocompleteLocation>::class.java)
                // Do something with yourObject
                println(cities[4].name)
                cities
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } else {
            println("Failed to load JSON data from the file.")
            null
        }
    }
}
