package com.example.photoweather.photodetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.photoweather.R
import com.example.photoweather.data.cache.models.Photo
import kotlinx.android.synthetic.main.fragment_photos_details.*
import java.text.SimpleDateFormat
import java.util.*

class PhotoDetailsFragment : Fragment(R.layout.fragment_photos_details) {

    private var photo: Photo? = null

    companion object {
        private const val KEY_PHOTO = "photo"

        fun newInstance(photo: Photo): PhotoDetailsFragment {
            val fragment = PhotoDetailsFragment()

            val bundle = Bundle()
            bundle.putSerializable(KEY_PHOTO, photo)

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photo = requireArguments().getSerializable(KEY_PHOTO) as Photo?
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(photo)
    }

    private fun initView(photo: Photo?) {
        photo?.let {
            setPhotoData(it)
        }

        flBackground.setOnClickListener {
            // Do Nothing
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_share -> {
                activity?.startActivity(createShareIntent())
                return true
            }
        }

        return false
    }

    private fun createShareIntent(): Intent {
        return Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, Uri.parse(photo?.imagePath))
            type = "image/*"
        }
    }

    private fun setPhotoData(photo: Photo) {
        Glide.with(requireContext())
            .load(Uri.parse(photo.imagePath))
            .placeholder(R.drawable.ic_photo_placeholder)
            .into(ivPhoto)

        tvLocation.text =
            String.format(Locale.getDefault(), "%s, %s", photo.city, photo.country)

        val sdf = SimpleDateFormat("EEE, MMM d, ''yy", Locale.getDefault())
        tvDate.text = sdf.format(photo.date)

        tvTemp.text = String.format("%d°C", photo.temp)

        tvTempDesc.text = photo.tempDesc
            .split(" ")
            .joinToString(" ") { it.capitalize(Locale.getDefault()) }
            .trimEnd()

        if (photo.tempIconUrl.isNotEmpty()) {
            Glide.with(requireContext())
                .load(photo.tempIconUrl)
                .into(ivTempIcon)
        }
    }
}

