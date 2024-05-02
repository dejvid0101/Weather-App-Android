package me.projects.firstandroidapp.network
import me.projects.firstandroidapp.models.AutocompleteLocation
import me.projects.firstandroidapp.models.ForecastDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiSvc {
    @GET("v1/forecast.json?key=b354bae2692245ab994190139241103&")
    suspend fun getForecastForCity(@Query("q") city: String, @Query("days") days: String): ForecastDTO

    @GET("core/world-cities/world-cities_json/data/5b3dd46ad10990bca47b04b4739a02ba/world-cities_json.json")
    suspend fun getCities(): Array<AutocompleteLocation>
}