package com.example.photoweather.utils

import com.example.photoweather.data.cache.models.Photo
import com.example.photoweather.data.remote.models.Weather

object PhotoWeatherMapper {

    fun toPhoto(imagePath: String, weather: Weather) = Photo(
        id = imagePath + weather.cityName + weather.countryCode,
        imagePath = imagePath,
        temp = weather.temperature.temp,
        tempIconUrl = if (weather.weatherInfo.isNotEmpty()) weather.weatherInfo[0].weatherIcon else "",
        tempDesc = if (weather.weatherInfo.isNotEmpty()) weather.weatherInfo[0].weatherDesc else "",
        city = weather.cityName,
        country = weather.countryCode.countryCode
    )
}