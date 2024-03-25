package me.projects.firstandroidapp

import me.projects.firstandroidapp.adapter.WeatherItemAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import me.projects.firstandroidapp.databinding.ActivityMainBinding
import me.projects.firstandroidapp.interfaces.OnItemClickListener

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityMainBinding;

    private lateinit var itemAdapter: WeatherItemAdapter
    private lateinit var itemList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

        setupRecyclerView()


    }

    private fun setupRecyclerView() {

        itemList= List(50) { index -> "${index + 1} °C" }

        binding.recyclerView.apply {
            layoutManager=LinearLayoutManager(this@MainActivity);
            adapter = WeatherItemAdapter(itemList, this@MainActivity)
        }

    }

    override fun onItemClick(day: String) {
        Toast.makeText(this, "Day: $day", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@MainActivity, DayForecastActivity::class.java))
    }

}

