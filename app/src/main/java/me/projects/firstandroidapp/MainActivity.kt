package me.projects.firstandroidapp

import me.projects.firstandroidapp.adapter.WeatherItemAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.projects.firstandroidapp.databinding.ActivityMainBinding
import me.projects.firstandroidapp.interfaces.OnItemClickListener
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
import java.net.SocketTimeoutException

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityMainBinding;
    private lateinit var forecast: ForecastDTO
    private var hourlyRecyclerView: RecyclerView?=null
    private var dailyRecyclerView: RecyclerView?=null
    private var savedScrollPositionHourly: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)
        setupRecyclerView(savedInstanceState)
    }

    private fun setupRecyclerView(savedInstanceState: Bundle?) {


        // Start a new coroutine on the main thread
        CoroutineScope(Dispatchers.IO).launch {
            // Fetch forecast data

            try {
                forecast = fetchForecast()
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


    override fun onSaveInstanceState(outState: Bundle) {
        // Save the current scroll position
         outState.putInt("scrollPositionHourly", ((binding.recyclerViewHourly.layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition())
        super.onSaveInstanceState(outState)
    }

    override fun onItemClick(isHourly: Boolean, itemPosition: Int) {

        CoroutineScope(Dispatchers.IO).launch {

                fetchForecast()
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

    private suspend fun fetchForecast(): ForecastDTO {
        return withContext(Dispatchers.IO) {
            // Create an instance of the API service
            val service = ApiClient.retrofit.create(ApiSvc::class.java)

            try {

                // Perform the API call asynchronously using suspend function
                forecast = service.getForecastForCity("Zag", "4")
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

