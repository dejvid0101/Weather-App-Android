import com.google.gson.annotations.SerializedName
import me.projects.firstandroidapp.models.CurrentWeather
import me.projects.firstandroidapp.models.Location

data class ForecastDTO(
    @SerializedName("location") val location: Location,
    @SerializedName("current") val current: CurrentWeather
)