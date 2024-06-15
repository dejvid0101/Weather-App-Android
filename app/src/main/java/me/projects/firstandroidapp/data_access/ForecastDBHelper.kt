import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import me.projects.firstandroidapp.models.Condition

class ForecastDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_LOCATIONS_TABLE = """
            CREATE TABLE ${ForecastContract.LocationEntry.TABLE_NAME} (
                ${ForecastContract.LocationEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${ForecastContract.LocationEntry.COLUMN_NAME_NAME} TEXT NOT NULL,
                ${ForecastContract.LocationEntry.COLUMN_NAME_REGION} TEXT,
                ${ForecastContract.LocationEntry.COLUMN_NAME_COUNTRY} TEXT,
                ${ForecastContract.LocationEntry.COLUMN_NAME_LATITUDE} REAL,
                ${ForecastContract.LocationEntry.COLUMN_NAME_LONGITUDE} REAL,
                ${ForecastContract.LocationEntry.COLUMN_NAME_TIMEZONE_ID} TEXT,
                ${ForecastContract.LocationEntry.COLUMN_NAME_LOCALTIME_EPOCH} INTEGER,
                ${ForecastContract.LocationEntry.COLUMN_NAME_LOCALTIME} TEXT
            )
        """

        val SQL_CREATE_CURRENT_WEATHER_TABLE = """
            CREATE TABLE ${ForecastContract.CurrentWeatherEntry.TABLE_NAME} (
                ${ForecastContract.CurrentWeatherEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${ForecastContract.CurrentWeatherEntry.COLUMN_NAME_LAST_UPDATED} TEXT NOT NULL,
                ${ForecastContract.CurrentWeatherEntry.COLUMN_NAME_TEMP_C} REAL,
                ${ForecastContract.CurrentWeatherEntry.COLUMN_NAME_TEMP_F} REAL,
                ${ForecastContract.CurrentWeatherEntry.COLUMN_NAME_IS_DAY} INTEGER,
                ${ForecastContract.CurrentWeatherEntry.COLUMN_NAME_WIND_KPH} REAL,
                ${ForecastContract.CurrentWeatherEntry.COLUMN_NAME_WIND_DIR} TEXT,
                ${ForecastContract.CurrentWeatherEntry.COLUMN_NAME_HUMIDITY} INTEGER,
                ${ForecastContract.CurrentWeatherEntry.COLUMN_NAME_CLOUD} INTEGER,
                ${ForecastContract.CurrentWeatherEntry.COLUMN_NAME_FEELSLIKE_C} REAL,
                ${ForecastContract.CurrentWeatherEntry.COLUMN_NAME_FEELSLIKE_F} REAL,
                ${ForecastContract.CurrentWeatherEntry.COLUMN_NAME_CONDITION_TEXT} TEXT,
                ${ForecastContract.CurrentWeatherEntry.COLUMN_NAME_CONDITION_ICON} TEXT
            )
        """

        val SQL_CREATE_DAILY_WEATHER_TABLE = """
            CREATE TABLE ${ForecastContract.DailyWeatherEntry.TABLE_NAME} (
                ${ForecastContract.DailyWeatherEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_MAXTEMP_C} REAL,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_MAXTEMP_F} REAL,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_MINTEMP_C} REAL,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_MINTEMP_F} REAL,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_AVGTEMP_C} REAL,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_AVGTEMP_F} REAL,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_MAXWIND_MPH} REAL,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_MAXWIND_KPH} REAL,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_TOTALPRECIP_MM} REAL,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_TOTALPRECIP_IN} REAL,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_TOTALSNOW_CM} REAL,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_AVGVIS_KM} REAL,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_AVGVIS_MILES} REAL,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_HUMIDITY} INTEGER,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_DAILY_WILL_IT_RAIN} INTEGER,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_DAILY_CHANCE_OF_RAIN} INTEGER,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_DAILY_WILL_IT_SNOW} INTEGER,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_DAILY_CHANCE_OF_SNOW} INTEGER,
                ${ForecastContract.DailyWeatherEntry.COLUMN_NAME_UV} REAL
            )
        """

        val SQL_CREATE_HOURLY_WEATHER_TABLE = """
            CREATE TABLE ${ForecastContract.HourEntry.TABLE_NAME} (
                ${ForecastContract.HourEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${ForecastContract.HourEntry.COLUMN_NAME_FORECAST_DAY_ID} INTEGER,
                ${ForecastContract.HourEntry.COLUMN_NAME_TIME} TEXT,
                ${ForecastContract.HourEntry.COLUMN_NAME_CONDITION_TEXT} TEXT,
                ${ForecastContract.HourEntry.COLUMN_NAME_CONDITION_ICON} TEXT,
                ${ForecastContract.HourEntry.COLUMN_NAME_TEMP_C} REAL,
                FOREIGN KEY (${ForecastContract.HourEntry.COLUMN_NAME_FORECAST_DAY_ID}) REFERENCES ${ForecastContract.DailyWeatherEntry.TABLE_NAME} (${ForecastContract.DailyWeatherEntry._ID})
            )
        """

        db.execSQL(SQL_CREATE_LOCATIONS_TABLE)
        db.execSQL(SQL_CREATE_CURRENT_WEATHER_TABLE)
        db.execSQL(SQL_CREATE_DAILY_WEATHER_TABLE)
        db.execSQL(SQL_CREATE_HOURLY_WEATHER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${ForecastContract.LocationEntry.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${ForecastContract.CurrentWeatherEntry.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${ForecastContract.DailyWeatherEntry.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${ForecastContract.HourEntry.TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "forecast.db"
        const val DATABASE_VERSION = 1
    }
}
