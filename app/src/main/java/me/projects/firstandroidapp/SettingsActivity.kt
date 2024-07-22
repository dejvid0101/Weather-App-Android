package me.projects.firstandroidapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import me.projects.firstandroidapp.adapter.AutocompleteCitiesAdapter
import me.projects.firstandroidapp.databinding.ActivityMainBinding
import me.projects.firstandroidapp.databinding.ActivitySettingsBinding
import me.projects.firstandroidapp.models.AutocompleteLocation
import me.projects.firstandroidapp.utils.JSONUtilz
import java.io.InputStream

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding;
    private lateinit var citiesInputStream: InputStream
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var citiesInputStream: InputStream= this@SettingsActivity.resources.openRawResource(R.raw.cities)
        var cities=JSONUtilz.loadAutocompleteCities(citiesInputStream)

        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.tvAutocompleteSettings)
        val suggestions: MutableList<String> = mutableListOf()

        // Extract names from AutocompleteLocation objects and add them to the mutable list for adapter compatibility
        if (cities != null) {
            for (location in cities) {
                suggestions.add(location.name)
            }
        }

        val adapter = AutocompleteCitiesAdapter(this, suggestions)
        autoCompleteTextView.setAdapter(adapter)
    }

    fun onSaveClicked(view: View) {
        val sharedPreferences = getSharedPreferences("DefLocation", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("city", binding.tvAutocompleteSettings.text.toString())
        editor.apply()
    }


}