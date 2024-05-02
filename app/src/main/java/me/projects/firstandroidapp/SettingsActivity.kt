package me.projects.firstandroidapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.TextView
import me.projects.firstandroidapp.adapter.AutocompleteCitiesAdapter

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.tvAutocompleteSettings)
        val suggestions = mutableListOf("Apple", "Banana", "Orange", "Pineapple", "Grapes")
        val adapter = AutocompleteCitiesAdapter(this, suggestions)
        autoCompleteTextView.setAdapter(adapter)
    }


}