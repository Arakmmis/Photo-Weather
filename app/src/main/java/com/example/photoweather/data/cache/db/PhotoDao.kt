package com.example.photoweather.data.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.photoweather.data.cache.constants.PhotoConstants
import com.example.photoweather.data.cache.models.Photo

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: Photo): Long

    @Query(PhotoConstants.QUERY_PHOTOS)
    fun getAllPhotos(): List<Photo>
}