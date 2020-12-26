package com.example.photoweather.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.photoweather.R
import com.example.photoweather.photoslist.PhotosListFragment
import com.example.photoweather.utils.LocationService

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<PhotosListFragment>(R.id.container)
            }
        }
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