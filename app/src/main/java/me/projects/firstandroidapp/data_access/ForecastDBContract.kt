import android.provider.BaseColumns

object ForecastContract {

    // Define table names and column names
    object LocationEntry : BaseColumns {
        const val _ID = "_id"
        const val TABLE_NAME = "locations"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_REGION = "region"
        const val COLUMN_NAME_COUNTRY = "country"
        const val COLUMN_NAME_LATITUDE = "latitude"
        const val COLUMN_NAME_LONGITUDE = "longitude"
        const val COLUMN_NAME_TIMEZONE_ID = "timezone_id"
        const val COLUMN_NAME_LOCALTIME_EPOCH = "localtime_epoch"
        const val COLUMN_NAME_LOCALTIME = "localtime"
    }

    object CurrentWeatherEntry : BaseColumns {
        const val _ID = "_id"
        const val TABLE_NAME = "current_weather"
        const val COLUMN_NAME_LAST_UPDATED = "last_updated"
        const val COLUMN_NAME_TEMP_C = "temp_c"
        const val COLUMN_NAME_TEMP_F = "temp_f"
        const val COLUMN_NAME_IS_DAY = "is_day"
        const val COLUMN_NAME_WIND_KPH = "wind_kph"
        const val COLUMN_NAME_WIND_DIR = "wind_dir"
        const val COLUMN_NAME_HUMIDITY = "humidity"
        const val COLUMN_NAME_CLOUD = "cloud"
        const val COLUMN_NAME_FEELSLIKE_C = "feelslike_c"
        const val COLUMN_NAME_FEELSLIKE_F = "feelslike_f"
        const val COLUMN_NAME_CONDITION_TEXT = "condition_text"
        const val COLUMN_NAME_CONDITION_ICON = "condition_icon"
    }

    object DailyWeatherEntry : BaseColumns {
        const val _ID = "_id"
        const val TABLE_NAME = "daily_weather"
        const val COLUMN_NAME_MAXTEMP_C = "maxtemp_c"
        const val COLUMN_NAME_MAXTEMP_F = "maxtemp_f"
        const val COLUMN_NAME_MINTEMP_C = "mintemp_c"
        const val COLUMN_NAME_MINTEMP_F = "mintemp_f"
        const val COLUMN_NAME_AVGTEMP_C = "avgtemp_c"
        const val COLUMN_NAME_AVGTEMP_F = "avgtemp_f"
        const val COLUMN_NAME_MAXWIND_MPH = "maxwind_mph"
        const val COLUMN_NAME_MAXWIND_KPH = "maxwind_kph"
        const val COLUMN_NAME_TOTALPRECIP_MM = "totalprecip_mm"
        const val COLUMN_NAME_TOTALPRECIP_IN = "totalprecip_in"
        const val COLUMN_NAME_TOTALSNOW_CM = "totalsnow_cm"
        const val COLUMN_NAME_AVGVIS_KM = "avgvis_km"
        const val COLUMN_NAME_AVGVIS_MILES = "avgvis_miles"
        const val COLUMN_NAME_HUMIDITY = "humidity"
        const val COLUMN_NAME_DAILY_WILL_IT_RAIN = "daily_will_it_rain"
        const val COLUMN_NAME_DAILY_CHANCE_OF_RAIN = "daily_chance_of_rain"
        const val COLUMN_NAME_DAILY_WILL_IT_SNOW = "daily_will_it_snow"
        const val COLUMN_NAME_DAILY_CHANCE_OF_SNOW = "daily_chance_of_snow"
        const val COLUMN_NAME_UV = "uv"
    }

    object HourEntry : BaseColumns {
        const val _ID = "_id"
        const val TABLE_NAME = "hourly_weather"
        const val COLUMN_NAME_FORECAST_DAY_ID = "forecast_day_id"
        const val COLUMN_NAME_TIME = "time"
        const val COLUMN_NAME_CONDITION_TEXT = "condition_text"
        const val COLUMN_NAME_CONDITION_ICON = "condition_icon"
        const val COLUMN_NAME_TEMP_C = "temp_c"
    }
}
