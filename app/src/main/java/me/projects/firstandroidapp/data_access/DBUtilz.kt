package me.projects.firstandroidapp.data_access

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import me.projects.firstandroidapp.models.ForecastDTO
import java.time.LocalDateTime

class DBUtilz {
    companion object {
        fun getMostRecentLocationRow(database: SQLiteDatabase): Cursor? {
            val query =
                "SELECT * FROM ${ForecastContract.LocationEntry.TABLE_NAME} ORDER BY ${ForecastContract.LocationEntry._ID} DESC LIMIT 1"
            return database.rawQuery(query, null)
        }

        fun getMostRecentCurrentWeatherRow(database: SQLiteDatabase): Cursor? {
            val query =
                "SELECT * FROM ${ForecastContract.CurrentWeatherEntry.TABLE_NAME} ORDER BY ${ForecastContract.CurrentWeatherEntry._ID} DESC LIMIT 1"
            return database.rawQuery(query, null)
        }



        fun insertWeatherDataRow(database: SQLiteDatabase, forecast: ForecastDTO) {


            // Insert the data into the database
            database.insert(ForecastContract.LocationEntry.TABLE_NAME, null, ContentValues().apply {
                 put(ForecastContract.LocationEntry.COLUMN_NAME_NAME, forecast.location.name)
                 // Add other column-value pairs here
             })
             database.insert(ForecastContract.CurrentWeatherEntry.TABLE_NAME, null, ContentValues().apply {
                 put(ForecastContract.CurrentWeatherEntry.COLUMN_NAME_TEMP_C, forecast.current.tempC.toString())
                 put(ForecastContract.CurrentWeatherEntry.COLUMN_NAME_LAST_UPDATED, LocalDateTime.now().toString())
                 put(ForecastContract.CurrentWeatherEntry.COLUMN_NAME_CONDITION_ICON, forecast.current.condition.icon)
                 put(ForecastContract.CurrentWeatherEntry.COLUMN_NAME_CONDITION_TEXT, forecast.current.condition.text)
                 // Add other column-value pairs here
             })
        }


    }
}