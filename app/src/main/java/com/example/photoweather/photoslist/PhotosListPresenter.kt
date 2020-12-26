package com.example.photoweather.photoslist

import android.util.Log
import com.example.photoweather.data.manager.DataManager
import com.example.photoweather.data.manager.IDataManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject

class PhotosListPresenter(private val view: PhotosContract.View)
    : PhotosContract.Presenter {

    private val TAG = "PhotosListPresenter"
    private val disposables = CompositeDisposable()

    private val dataManager: IDataManager by inject(DataManager::class.java)

    init {
        getPhotos()
    }

    private fun getPhotos() {
        view.showLoading(true)

        disposables.add(
            dataManager.getPhotos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { photos, e ->
                    if (e != null) {
                        Log.e(TAG, e.localizedMessage ?: e.stackTraceToString())
                        view.showNoResults()
                        view.showLoading(false)
                        return@subscribe
                    }

                    view.showPhotos(photos)
                    view.showLoading(false)
                }
        )
    }
}