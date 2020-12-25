package com.example.photoweather.remote

import com.example.photoweather.remote.constants.WeatherServiceConstants
import com.example.photoweather.remote.models.Weather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(WeatherServiceConstants.CURRENT_WEATHER_INFO_URL)
    fun getCurrentWeather(@Query("q") cityName: String): Single<Weather>
}