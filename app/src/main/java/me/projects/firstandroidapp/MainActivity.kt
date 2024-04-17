package me.projects.firstandroidapp

import me.projects.firstandroidapp.adapter.WeatherItemAdapter
import android.content.Intent
import android.os.Bundle
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
import me.projects.firstandroidapp.models.ForecastDTO
import me.projects.firstandroidapp.network.ApiClient
import me.projects.firstandroidapp.network.ApiSvc

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

            val forecast = fetchForecast()

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

    override fun onSaveInstanceState(outState: Bundle) {
        // Save the current scroll position
         outState.putInt("scrollPositionHourly", ((binding.recyclerViewHourly.layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition())
        super.onSaveInstanceState(outState)
    }

    override fun onItemClick(temp: String) {

        CoroutineScope(Dispatchers.IO).launch {fetchForecast()

        }

        //Toast.makeText(this, "Day: $temp", Toast.LENGTH_SHORT).show()
        startActivity(
            Intent(
                this@MainActivity,
                DayForecastActivity::class.java
            ).putExtra("temperature", temp)
        )

    }

    private suspend fun fetchForecast(): ForecastDTO {
        return withContext(Dispatchers.IO) {
            // Create an instance of the API service
            val service = ApiClient.retrofit.create(ApiSvc::class.java)

            try {

                // Perform the API call asynchronously using suspend function
                val forecast = service.getForecastForCity("Zag", "4")
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

