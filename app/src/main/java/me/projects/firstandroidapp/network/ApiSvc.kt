package me.projects.firstandroidapp.network
import ForecastDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiSvc {
    @GET("v1/forecast.json?key=b354bae2692245ab994190139241103")
    suspend fun getForecastForCity(@Query("q") city: String, @Query("days") days: String): ForecastDTO
}