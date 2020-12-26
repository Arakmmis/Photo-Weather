package com.example.photoweather.photoslist

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoweather.R
import com.example.photoweather.data.cache.models.Photo
import com.example.photoweather.photoslist.adapter.PhotoViewHolder
import com.example.photoweather.photoslist.adapter.PhotosAdapter
import com.example.photoweather.utils.Error
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_photos_list.*
import kotlinx.android.synthetic.main.view_loading.*
import kotlinx.android.synthetic.main.view_no_results.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PhotosListFragment : Fragment(R.layout.fragment_photos_list), PhotosContract.View,
    PhotoViewHolder.Callback {

    private val TAG = "PhotosListFragment"

    private lateinit var adapter: PhotosAdapter
    private lateinit var presenter: PhotosContract.Presenter

    private val REQUEST_IMAGE_CAPTURE = 1

    private lateinit var photoURI: Uri

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

    override fun showNoResults() {
        llNoResults.visibility = View.VISIBLE
        llLoading.visibility = View.GONE
        rvPhotos.visibility = View.GONE
    }

    override fun showLoading(isLoading: Boolean) {
        llLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
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

    override fun getUserLocation() {

    }

    override fun showError(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showInsertionSucceeded() {
        Toast.makeText(context, "Photo has been added successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onPhotoSelected(photo: Photo) {
        this.view?.let {
            Snackbar.make(
                it,
                "You have clicked on photo: ${photo.city}, ${photo.country}",
                Snackbar.LENGTH_LONG
            )
        }
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->

            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                Log.e(TAG, ex.localizedMessage ?: ex.stackTraceToString())
                null
            }

            photoFile?.also {
                photoURI = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.android.fileprovider",
                    it
                )

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                } catch (ex: ActivityNotFoundException) {
                    showError(Error.NO_APP_FOUND)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            presenter.newPhoto(photoURI.toString())
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())

        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }
}