package com.example.photoweather.data.manager

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.photoweather.data.cache.db.PhotosDatabase
import com.example.photoweather.testutils.DataUtils
import com.example.photoweather.testutils.MockWeatherService
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataManagerTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dataManager: IDataManager

    @Before
    fun prepare() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(
            context, PhotosDatabase::class.java
        ).build()
        val photoDao = db.getPhotoDao()

        val weatherService = MockWeatherService()

        dataManager = DataManager(photoDao, weatherService)
    }

    @Test
    fun onInsertionCallDaoInsert() {
        val photo = DataUtils.createPhotos(1)[0]

        var resultId: Long = -1
        dataManager.insertPhoto(photo).subscribe { id -> resultId = id }

        assertThat(resultId, not(-1))
    }

    @Test
    fun onGetWeatherSuccessReturnWeather() {
        val weather = dataManager
            .getWeather(21.422487, 39.826206)
            .blockingGet()
        val mockWeather = DataUtils.createWeather(1)[0]

        assertThat(weather, equalTo(mockWeather))
    }

    @Test
    fun onGetPhotosSuccessReturnListOfPhotosWithWeather() {
        val mockPhotos = DataUtils.createPhotosWithWeather(5)

        mockPhotos.forEach { photo -> dataManager.insertPhoto(photo).blockingGet() }

        assertThat(dataManager.getPhotos().blockingGet(), equalTo(mockPhotos))
    }
}