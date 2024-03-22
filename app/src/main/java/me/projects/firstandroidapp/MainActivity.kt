package me.projects.firstandroidapp

import WeatherItemAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import me.projects.firstandroidapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
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
            adapter = WeatherItemAdapter(itemList)
        }

    }

}

