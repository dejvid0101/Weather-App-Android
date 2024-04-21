package me.projects.firstandroidapp// NewActivity.kt
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import me.projects.firstandroidapp.databinding.DayForecastBinding
import me.projects.firstandroidapp.models.DailyWeather
import me.projects.firstandroidapp.models.ForecastDTO

class DayForecastActivity : AppCompatActivity() {
    private lateinit var binding: DayForecastBinding
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DayForecastBinding.inflate(layoutInflater);
        setContentView(binding.root)

        processIntentExtras(intent)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun processIntentExtras(intent: Intent) {
        // Check if the Intent has extra data
        if (intent.hasExtra("dailyForecast")&&intent.hasExtra("info")) {
            // Retrieve the string extra data from the Intent
            val dailyForecast = intent.getSerializableExtra("dailyForecast", DailyWeather::class.java)
            val info = intent.getSerializableExtra("info", ForecastDTO::class.java)

            // Now you can use the extraData variable in your activity
            // For example, you can set it to a TextView
            if (dailyForecast != null&& info != null) {
                findViewById<TextView>(binding.textCurrentTemperature.id).text = dailyForecast.tempC.toString()
                findViewById<TextView>(binding.tvTemp.id).text = dailyForecast.dailyChanceOfRain.toString()+"%"
                findViewById<TextView>(binding.textLocationName.id).text = info.location.name
                findViewById<TextView>(binding.tvHumidity.id).text = dailyForecast.humidity.toString()+"%"

                val imageUrl = "https://" + dailyForecast.condition.icon
                Glide.with(this)
                    .load(imageUrl)
                    .into(binding.imageWeatherIcon)
            }
        }
    }

}
