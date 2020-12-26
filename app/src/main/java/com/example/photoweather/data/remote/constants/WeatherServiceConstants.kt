package com.example.photoweather.data.remote.constants

object WeatherServiceConstants {

    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    const val CURRENT_WEATHER_INFO_URL =
        "weather?units=metric&appid=57447ba7862d011135b2d46d416ae09b"

    fun getWeatherIconUrl(id: String) = "https://openweathermap.org/img/wn/$id@2x.png"
}