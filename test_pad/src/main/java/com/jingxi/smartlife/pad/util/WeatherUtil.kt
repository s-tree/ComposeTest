package com.jingxi.smartlife.pad.util

import android.text.TextUtils
import com.jingxi.smartlife.pad.compose.R
import java.lang.reflect.Field

class WeatherUtil {

    class WeatherState internal constructor(
        var city: String = "南通",
        var temperature: String = "16~19",
        var weather: String = "多云",
        var weatherCode: Int = 102
    )

    companion object{

        val codeMap = mapOf(
            100 to "00",
            101 to "01",
            102 to "01",
            103 to "01",
            104 to "02",
            300 to "03",
            301 to "03",
            302 to "04",
            303 to "04",
            304 to "05",
            406 to "06",
            305 to "07",
            309 to "07",
            306 to "08",
            307 to "09",
            308 to "09",
            310 to "10",
            311 to "11",
            312 to "12",
            407 to "13",
            400 to "14",
            401 to "15",
            402 to "16",
            403 to "17",
            501 to "18",
            500 to "18",
            509 to "18",
            510 to "18",
            514 to "18",
            515 to "18",
            313 to "19",
            507 to "20",
            314 to "21",
            315 to "22",
            316 to "23",
            317 to "24",
            318 to "25",
            408 to "26",
            409 to "27",
            410 to "28",
            504 to "29",
            503 to "30",
            508 to "31",
            502 to "32",
            511 to "32",
            512 to "32",
            513 to "32",
            399 to "301",
            404 to "302",
            405 to "302",
            499 to "302"
        )

        fun getWeatherIcon(weatherState: WeatherState): Int {
            val fileName = TextUtils.concat("weather_", codeMap[weatherState.weatherCode]).toString()
            try {
                val field: Field = R.mipmap::class.java.getField(fileName)
                if (field != null) {
                    return field.getInt(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return R.mipmap.weather_00
        }
    }

    fun observeWeather(state: WeatherState){

    }
}