package me.projects.firstandroidapp// NewActivity.kt
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import me.projects.firstandroidapp.databinding.DayForecastBinding

class DayForecastActivity : AppCompatActivity() {
    private lateinit var binding: DayForecastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DayForecastBinding.inflate(layoutInflater);
        setContentView(binding.root)

        processIntentExtras(intent)
    }

    private fun processIntentExtras(intent: Intent) {
        // Check if the Intent has extra data
        if (intent.hasExtra("temperature")) {
            // Retrieve the string extra data from the Intent
            val extraData = intent.getStringExtra("temperature")

            // Now you can use the extraData variable in your activity
            // For example, you can set it to a TextView
            val textView: TextView = findViewById(binding.tvTemp.id)
            textView.text = extraData
        }
    }

}
