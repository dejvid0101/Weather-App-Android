package me.projects.firstandroidapp

import me.projects.firstandroidapp.adapter.WeatherItemAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.projects.firstandroidapp.databinding.ActivityMainBinding
import me.projects.firstandroidapp.interfaces.OnItemClickListener
import me.projects.firstandroidapp.network.ApiClient
import me.projects.firstandroidapp.network.ApiSvc

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityMainBinding;
    private lateinit var itemList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

        fetchForecast()
        setupRecyclerView()


    }

    private fun setupRecyclerView() {

        itemList = List(50) { index -> "${index + 1} °C" }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity);
            adapter = WeatherItemAdapter(itemList, this@MainActivity)
        }

    }

    override fun onItemClick(temp: String) {

        //Toast.makeText(this, "Day: $temp", Toast.LENGTH_SHORT).show()
        startActivity(
            Intent(
                this@MainActivity,
                DayForecastActivity::class.java
            ).putExtra("temperature", temp)
        )

    }

    private fun fetchForecast() {
        // Start a new coroutine
        CoroutineScope(Dispatchers.IO).launch {
            // Create an instance of the API service
            val service = ApiClient.retrofit.create(ApiSvc::class.java)

            try {
                // Perform the API call asynchronously using suspend function
                val forecast = service.getForecastForCity("Zag", "1")

                // Handle the result here
                // For example, print the user data
                binding.textCurrentTemperature.text = forecast.current.tempC.toString() + "°C"
            } catch (e: Exception) {
                // Handle any exceptions
                println("Error fetching forecast: ${e.message}")
            }
        }

    }
}

