package com.example.photoweather.cache.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.photoweather.cache.models.Photo
import com.example.photoweather.utils.TestUtils
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PhotosDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userDao: PhotoDao
    private lateinit var db: PhotosDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, PhotosDatabase::class.java
        ).build()
        userDao = db.getPhotoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertPhotoAndReadInList() {
        val photo: Photo = TestUtils.createPhotos(1)[0].apply {
            this.id = "photo-#1"
        }

        userDao.insert(photo)

        val photosById = userDao.getAllPhotos().filter { it.id == "photo-#1" }

        assertThat(photosById[0], equalTo(photo))
    }

    @Test
    @Throws(Exception::class)
    fun insertManyPhotosAndReadList() {
        var index = 0
        val photos: List<Photo> = TestUtils.createPhotos(3).onEach {
            it.id = "photo-#${index++}"
        }

        for (i in 0 until index)
            userDao.insert(photos[i])

        val dbPhotos = userDao.getAllPhotos()

        dbPhotos.forEachIndexed { i, photo ->
            assertThat(photo, equalTo(photos[i]))
        }
    }
}