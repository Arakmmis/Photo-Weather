package com.example.photoweather.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.photoweather.cache.mapper.DateConverter
import com.example.photoweather.cache.models.Photo

@Database(entities = [Photo::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class PhotosDatabase : RoomDatabase() {

    abstract fun getPhotoDao(): PhotoDao

    companion object {

        @Volatile
        private var INSTANCE: PhotosDatabase? = null

        fun getDatabase(context: Context): PhotosDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhotosDatabase::class.java,
                    "photo_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}