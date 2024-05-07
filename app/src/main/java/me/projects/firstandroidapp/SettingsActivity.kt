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

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.tvAutocompleteSettings)
        val suggestions = mutableListOf("Apple", "Banana", "Orange", "Pineapple", "Grapes")
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