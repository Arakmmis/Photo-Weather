package com.example.photoweather.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("name")
    @Expose
    val cityName: String,
    @SerializedName("weather")
    @Expose
    val weatherInfo: List<WeatherInfo>,
    @SerializedName("main")
    @Expose
    val temperature: Temperature,
    @SerializedName("sys")
    @Expose
    val countryCode: CountryCode
) {

    data class WeatherInfo(
        @SerializedName("description")
        @Expose
        val weatherDesc: String,
        @SerializedName("icon")
        @Expose
        var weatherIcon: String
    )

    data class Temperature(
        @SerializedName("temp")
        @Expose
        val temp: Float
    )

    data class CountryCode(
        @SerializedName("country")
        @Expose
        val countryCode: String
    )
}