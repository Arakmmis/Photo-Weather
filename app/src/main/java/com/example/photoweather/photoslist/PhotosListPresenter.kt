package com.example.photoweather.photoslist

import android.util.Log
import com.example.photoweather.data.cache.models.Photo
import com.example.photoweather.data.manager.DataManager
import com.example.photoweather.data.manager.IDataManager
import com.example.photoweather.utils.Error
import com.example.photoweather.utils.PhotoWeatherMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject

class PhotosListPresenter(private val view: PhotosContract.View) : PhotosContract.Presenter {

    private val TAG = "PhotosListPresenter"
    private val disposables = CompositeDisposable()

    private val dataManager: IDataManager by inject(DataManager::class.java)

    private lateinit var uriPath: String
    private var photos = emptyList<Photo>()

    init {
        getPhotosFromDb()
    }

    private fun getPhotosFromDb() {
        view.showLoading()

        disposables.add(
            dataManager.getPhotos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { photos, e ->
                    if (e != null) {
                        Log.e(TAG, e.localizedMessage ?: e.stackTraceToString())
                        view.showPhotos(emptyList())
                        view.hideLoading()
                        return@subscribe
                    }

                    this.photos = photos
                    view.showPhotos(photos)
                    view.hideLoading()
                }
        )
    }

    override fun getPhotos(): List<Photo> = photos

    override fun newPhoto(uriPath: String) {
        if (uriPath.isEmpty())
            view.showError(Error.FAILED_TO_SAVE)
        else {
            this.uriPath = uriPath
            view.showLoading()
            view.getUserLocation()
        }
    }

    override fun getWeather(lat: Double, lon: Double) {
        view.showLoading()

        disposables.add(
            dataManager.getWeather(lat, lon)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { weather, e ->
                    if (e != null) {
                        Log.e(TAG, e.localizedMessage ?: e.stackTraceToString())
                        view.showError(Error.FAILED_TO_RETRIEVE_WEATHER)
                        view.showPhotos(getPhotos())
                        view.hideLoading()
                        return@subscribe
                    }

                    insertPhotoInDb(PhotoWeatherMapper.toPhoto(uriPath, weather))
                }
        )
    }

    private fun insertPhotoInDb(photo: Photo) {
        view.showLoading()

        disposables.add(
            dataManager.insertPhoto(photo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { _, e ->
                    if (e != null) {
                        Log.e(TAG, e.localizedMessage ?: e.stackTraceToString())
                        view.showError(Error.FAILED_TO_SAVE)
                        view.showPhotos(getPhotos())
                        view.hideLoading()
                        return@subscribe
                    }

                    view.showInsertionSucceeded()
                    view.hideLoading()
                    getPhotosFromDb()
                }
        )
    }
}