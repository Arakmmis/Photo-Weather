package com.example.photoweather.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.photoweather.R
import com.example.photoweather.data.cache.models.Photo
import com.example.photoweather.photodetails.PhotoDetailsFragment
import com.example.photoweather.photoslist.PhotosListFragment
import com.example.photoweather.utils.LocationService

class HomeActivity : AppCompatActivity() {

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        showFragment(savedInstanceState, null)
    }

    private fun showFragment(savedInstanceState: Bundle?, fragment: Fragment?) {
        currentFragment = fragment

        if (savedInstanceState == null) {
            if (fragment == null || fragment is PhotosListFragment) {
                supportFragmentManager.commit {
                    fragment?.let {
                        replace(R.id.container, fragment)
                    } ?: replace<PhotosListFragment>(R.id.container)
                }
            } else {
                supportFragmentManager.commit {
                    add(R.id.container, fragment)
                }
            }
        }
    }

    fun openPhotoDetails(photo: Photo) {
        val fragment = PhotoDetailsFragment.newInstance(photo)
        showFragment(null, fragment)
    }

    override fun onBackPressed() {
        if (currentFragment is PhotoDetailsFragment)
            showFragment(null, null)
        else
            super.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LocationService.ACCESS_FINE_LOCATION
            && supportFragmentManager.findFragmentById(R.id.container) is PhotosListFragment
        )
            (supportFragmentManager.findFragmentById(R.id.container) as PhotosListFragment)
                .onRequestPermissionsResult(requestCode, permissions, grantResults)
        else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}