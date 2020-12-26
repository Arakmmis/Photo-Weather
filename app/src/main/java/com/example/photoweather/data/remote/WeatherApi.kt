package com.example.photoweather.data.remote

import com.example.photoweather.data.remote.constants.WeatherServiceConstants
import com.example.photoweather.data.remote.models.Weather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(WeatherServiceConstants.CURRENT_WEATHER_INFO_URL)
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Single<Weather>
}