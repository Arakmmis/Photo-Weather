package com.example.photoweather.utils

import com.example.photoweather.cache.models.Photo
import java.util.*

class TestUtils {

    companion object {

        fun createPhotos(amount: Int): List<Photo> {
            val photos = ArrayList<Photo>(amount)

            for (i in 0 until amount) {
                photos.add(Photo())
            }

            return photos
        }
    }
}