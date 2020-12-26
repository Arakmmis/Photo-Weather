package com.example.photoweather.photoslist

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoweather.R
import com.example.photoweather.data.cache.models.Photo
import com.example.photoweather.photoslist.adapter.PhotoViewHolder
import com.example.photoweather.photoslist.adapter.PhotosAdapter
import com.example.photoweather.utils.Error
import com.example.photoweather.utils.FileUtils
import com.example.photoweather.utils.LocationService
import kotlinx.android.synthetic.main.fragment_photos_list.*
import kotlinx.android.synthetic.main.view_loading.*
import kotlinx.android.synthetic.main.view_no_results.*

class PhotosListFragment : Fragment(R.layout.fragment_photos_list), PhotosContract.View,
    PhotoViewHolder.Callback, LocationService.CallBack {

    private val TAG = "PhotosListFragment"

    private lateinit var adapter: PhotosAdapter
    private lateinit var presenter: PhotosContract.Presenter

    private var locationService: LocationService? = null

    private val REQUEST_IMAGE_CAPTURE = 1

    private lateinit var photoUri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        presenter = PhotosListPresenter(this)
    }

    private fun initView() {
        val layoutManager = GridLayoutManager(context, 2)
        rvPhotos.layoutManager = layoutManager

        adapter = PhotosAdapter(emptyList(), this)
        rvPhotos.adapter = adapter

        btnTakePicture.setOnClickListener { openCamera() }
    }

    private fun showNoResults() {
        llNoResults.visibility = View.VISIBLE
        llLoading.visibility = View.GONE
        rvPhotos.visibility = View.GONE
    }

    override fun showLoading() {
        llLoading.visibility = View.VISIBLE
        llNoResults.visibility = View.GONE
        rvPhotos.visibility = View.GONE
    }

    override fun hideLoading() {
        llLoading.visibility = View.GONE
    }

    override fun showPhotos(photos: List<Photo>) {
        if (photos.isEmpty()) {
            showNoResults()
            return
        }

        llNoResults.visibility = View.GONE
        llLoading.visibility = View.GONE
        rvPhotos.visibility = View.VISIBLE

        adapter.updatePhotos(photos = photos)
    }

    override fun showError(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showInsertionSucceeded() {
        Toast.makeText(context, "Photo has been added successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onPhotoSelected(photo: Photo) {
        Toast.makeText(
            context,
            "You have clicked on photo: ${photo.city}, ${photo.country}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                FileUtils.getImageFile(requireContext(), TAG))

            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (ex: ActivityNotFoundException) {
                showError(Error.NO_APP_FOUND)
            }
        }
    }

    override fun getUserLocation() {
        locationService = LocationService(requireActivity(), this)
    }

    override fun onLocationAvailable(location: Location?) {
        location?.let {
            presenter.getWeather(it.latitude, it.longitude)
        }
    }

    override fun onLocationDenied() {
        showError(Error.FAILED_TO_RETRIEVE_LOCATION)
        hideLoading()
        showPhotos(presenter.getPhotos())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
            presenter.newPhoto(photoUri.toString())
        else if (locationService != null && requestCode == LocationService.ACCESS_FINE_LOCATION)
            locationService?.onActivityResult(requestCode, resultCode)
        else
            super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        locationService?.onRequestPermissionsResult(requestCode, grantResults)
    }
}