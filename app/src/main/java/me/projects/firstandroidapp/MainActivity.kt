package me.projects.firstandroidapp

import android.content.Context
import me.projects.firstandroidapp.adapter.WeatherItemAdapter
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.projects.firstandroidapp.adapter.AutocompleteCitiesAdapter
import me.projects.firstandroidapp.databinding.ActivityMainBinding
import me.projects.firstandroidapp.interfaces.OnItemClickListener
import me.projects.firstandroidapp.models.AutocompleteLocation
import me.projects.firstandroidapp.models.Condition
import me.projects.firstandroidapp.models.CurrentWeather
import me.projects.firstandroidapp.models.DailyWeather
import me.projects.firstandroidapp.models.ForecastDTO
import me.projects.firstandroidapp.models.ForecastDay
import me.projects.firstandroidapp.models.ForecastDays
import me.projects.firstandroidapp.models.Hour
import me.projects.firstandroidapp.models.HourlyCondition
import me.projects.firstandroidapp.models.Location
import me.projects.firstandroidapp.network.ApiClient
import me.projects.firstandroidapp.network.ApiSvc
import java.io.File
import java.io.InputStream

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityMainBinding;
    private lateinit var forecast: ForecastDTO
    private var hourlyRecyclerView: RecyclerView?=null
    private var dailyRecyclerView: RecyclerView?=null
    private var savedScrollPositionHourly: Int = 0
    private lateinit var citiesInputStream: InputStream;
    private lateinit var cities: Array<AutocompleteLocation>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)
        setupRecyclerView(savedInstanceState, "Bucuresti")
        citiesInputStream= this@MainActivity.resources.openRawResource(R.raw.cities)

        GlobalScope.launch(Dispatchers.Default){
            loadAutocompleteCities()

            withContext(Dispatchers.Main){
                setAutocompleteAdapter()
            }
        }

    }

    private fun setAutocompleteAdapter() {
        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autocomplete_search)

        val namesList: MutableList<String> = mutableListOf()

        // Extract names from AutocompleteLocation objects and add them to the mutable list for adapter compatibility
        for (location in cities) {
            namesList.add(location.name)
        }

        val adapter = AutocompleteCitiesAdapter(this, namesList)
        autoCompleteTextView.setAdapter(adapter)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item1 -> {
                // Start the new activity
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }


    private fun setupRecyclerView(savedInstanceState: Bundle?, city: String) {


        // Start a new coroutine on the main thread
        CoroutineScope(Dispatchers.IO).launch {
            // Fetch forecast data

            try {
                forecast = fetchForecast(city)
            } catch  (e: Exception) {
                withContext(Dispatchers.Main) {

                    Toast.makeText(
                        this@MainActivity,
                        "Failed to fetch weather data",
                        Toast.LENGTH_LONG
                    ).show()
                }


                //initialize lateinit property to placeholder object
                forecast = ForecastDTO(
                    Location("", "", "", 0.0, 0.0, "", 0L, ""),
                    CurrentWeather("", 0.0, 0.0, 0, Condition("", ""), 0.0, "", 0, 0, 0.0, 0.0),
                    ForecastDays(listOf(ForecastDay("", 0L, DailyWeather(
                        tempC = 0.0,
                        tempF = 0.0,
                        mintempC = 0.0,
                        mintempF = 0.0,
                        avgtempC = 0.0,
                        avgtempF = 0.0,
                        maxwindMph = 0.0,
                        maxwindKph = 0.0,
                        totalprecipMm = 0.0,
                        totalprecipIn = 0.0,
                        totalsnowCm = 0.0,
                        avgvisKm = 0.0,
                        avgvisMiles = 0.0,
                        humidity = 0,
                        dailyWillItRain = 0,
                        dailyChanceOfRain = 0,
                        dailyWillItSnow = 0,
                        dailyChanceOfSnow = 0,
                        condition = Condition("", ""),
                        uv = 0.0,
                        hours = emptyList()
                    ), listOf(Hour("", HourlyCondition("", ""), 0.0))))

                    )
                )
            }

            // Read the saved scroll position from savedInstanceState
            savedInstanceState?.let {
                savedScrollPositionHourly = it.getInt("scrollPositionHourly", 0)
            }

            // Set up RecyclerViews
            withContext(Dispatchers.Main) {
                hourlyRecyclerView = binding.recyclerViewHourly.apply {
                    layoutManager = hourlyRecyclerView?.layoutManager ?: LinearLayoutManager(this@MainActivity)
                    adapter = hourlyRecyclerView?.adapter ?: WeatherItemAdapter(forecast, this@MainActivity, true)
                }

                dailyRecyclerView = binding.recyclerViewDaily.apply {
                    layoutManager =  dailyRecyclerView?.layoutManager ?:LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                    adapter =  dailyRecyclerView?.adapter ?: WeatherItemAdapter(forecast, this@MainActivity, false)
                }

                println(savedScrollPositionHourly)

                binding.recyclerViewHourly.layoutManager?.scrollToPosition(savedScrollPositionHourly)

            }
        }
    }

    private fun loadAutocompleteCities(){
        val jsonContent = citiesInputStream.bufferedReader().use { it.readText() }

        // Check if JSON data is not null
        if (jsonContent != null) {
            try {
                // Parse JSON data using Gson
                val gson = Gson()
                cities = gson.fromJson(jsonContent, Array<AutocompleteLocation>::class.java)

                // Do something with yourObject
                println(cities[4].name)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            println("Failed to load JSON data from the file.")
        }
    }
    fun onShareClicked(view: View) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain" // Set the MIME type of the content you're sharing
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.forecast_share_subject) + " " + forecast.location.name + ": " + forecast.current.tempC + "°C")

        // Create an intent chooser to allow the user to select how to share the content
        val chooserIntent = Intent.createChooser(sharingIntent, "")

        // Verify that the intent resolves to an activity
        if (chooserIntent.resolveActivity(packageManager) != null) {
            // Start the intent chooser
            startActivity(chooserIntent)
        } else {
            Toast.makeText(this, "No app can handle this action", Toast.LENGTH_SHORT).show()
        }
    }

    fun onSaveClicked(view: View) {
        CoroutineScope(Dispatchers.IO).launch {

            forecast = fetchForecast(binding.autocompleteSearch.text.toString())

            withContext(Dispatchers.Main){
                (hourlyRecyclerView?.adapter as WeatherItemAdapter).change(forecast)
                (dailyRecyclerView?.adapter as WeatherItemAdapter).change(forecast)
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        // Save the current scroll position
         outState.putInt("scrollPositionHourly", ((binding.recyclerViewHourly.layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition())
        super.onSaveInstanceState(outState)
    }

    override fun onItemClick(isHourly: Boolean, itemPosition: Int) {

        CoroutineScope(Dispatchers.IO).launch {

                fetchForecast("Orlando")
        }

        if(!isHourly) {
            // launch DayForecast intent
            startActivity(
                Intent(
                    this@MainActivity,
                    DayForecastActivity::class.java
                )
                    .putExtra("dailyForecast", forecast.forecast.forecastday[itemPosition].day)
                    .putExtra("info", forecast)
            )
        }


    }

    private suspend fun fetchForecast(city: String): ForecastDTO {

        return withContext(Dispatchers.IO) {
            // Create an instance of the API service
            val service = ApiClient.retrofit.create(ApiSvc::class.java)

            try {

                // Perform the API call asynchronously using suspend function
                forecast = service.getForecastForCity(city, "4")
                println(forecast)

                forecast // Return the forecast object
            } catch (e: Exception) {
                // Handle any exceptions
                println("Error fetching forecast: ${e.message}")
                // You can throw the exception here or return a default forecast object
                throw e
            }
        }
    }
}

