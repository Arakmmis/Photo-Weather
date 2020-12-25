package com.example.photoweather.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.photoweather.cache.constants.PhotoConstants
import com.example.photoweather.cache.models.Photo

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: Photo)

    @Query(PhotoConstants.QUERY_PHOTOS)
    fun getAllPhotos(): List<Photo>
}