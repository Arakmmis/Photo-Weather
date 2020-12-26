package com.example.photoweather.testutils

import com.example.photoweather.data.cache.models.Photo
import com.example.photoweather.utils.PhotoWeatherMapper
import com.example.photoweather.data.remote.models.Weather

class DataUtils {

    companion object {

        fun createPhotos(count: Int): List<Photo> {
            val photos = ArrayList<Photo>(count)

            for (i in 0 until count) {
                photos.add(Photo())
            }

            return photos
        }

        fun createWeather(count: Int): List<Weather> {
            val listOfWeather = ArrayList<Weather>(count)

            for (i in 0 until count) {
                listOfWeather.add(getMockWeather(getMockCities()[i]))
            }

            return listOfWeather
        }

        fun createPhotosWithWeather(count: Int): List<Photo> {
            val photos = ArrayList<Photo>(count)

            var citiesCount = 0

            for (i in 0 until count) {
                if (citiesCount == 3) citiesCount = 0

                val weather = getMockWeather(getMockCities()[citiesCount++])

                photos.add(PhotoWeatherMapper.toPhoto("photo-#$i", weather))
            }

            return photos
        }

        private fun getMockCities() = listOf("Alexandria", "Tokyo", "Izmir")

        private fun getMockWeather(mockCity: String): Weather {
            return when (mockCity) {
                "Tokyo" -> Weather(
                    cityName = "Tokyo",
                    weatherInfo = listOf(
                        Weather.WeatherInfo(
                            weatherDesc = "Sunny",
                            weatherIcon = "01d"
                        )
                    ),
                    temperature = Weather.Temperature(-3),
                    countryCode = Weather.CountryCode("JP")
                )

                "Izmir" -> Weather(
                    cityName = "Izmir",
                    weatherInfo = listOf(
                        Weather.WeatherInfo(
                            weatherDesc = "Cloudy",
                            weatherIcon = "06d"
                        )
                    ),
                    temperature = Weather.Temperature(4),
                    countryCode = Weather.CountryCode("TR")
                )

                else -> Weather(
                    cityName = "Alexandria",
                    weatherInfo = listOf(
                        Weather.WeatherInfo(
                            weatherDesc = "Rainy",
                            weatherIcon = "10d"
                        )
                    ),
                    temperature = Weather.Temperature(20),
                    countryCode = Weather.CountryCode("EG")
                )
            }
        }
    }
}