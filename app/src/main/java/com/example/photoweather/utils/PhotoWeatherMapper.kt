package com.example.photoweather.utils

import com.example.photoweather.data.cache.models.Photo
import com.example.photoweather.data.remote.models.Weather
import kotlin.math.ceil

object PhotoWeatherMapper {

    val FALSE_TEMP = -100

    fun toPhoto(imagePath: String, weather: Weather?) = Photo(
        id = imagePath + weather?.cityName + weather?.countryCode,
        imagePath = imagePath,
        temp = weather?.let { ceil(weather.temperature.temp).toInt() } ?: FALSE_TEMP,
        tempIconUrl = weather?.let { if (weather.weatherInfo.isNotEmpty()) weather.weatherInfo[0].weatherIcon else "" }
            ?: "",
        tempDesc = weather?.let { if (weather.weatherInfo.isNotEmpty()) weather.weatherInfo[0].weatherDesc else "" }
            ?: "",
        city = weather?.cityName ?: "",
        country = weather?.countryCode?.countryCode ?: ""
    )
}