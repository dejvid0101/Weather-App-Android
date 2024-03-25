package me.projects.firstandroidapp// NewActivity.kt
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.projects.firstandroidapp.databinding.DayForecastBinding

class DayForecastActivity : AppCompatActivity() {
    private lateinit var binding: DayForecastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DayForecastBinding.inflate(layoutInflater);
        setContentView(binding.root)
    }
}
