package com.example.photoweather.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest

class LocationService(var activity: Activity?, private var callBack: CallBack?) {

    companion object {
        const val ACCESS_FINE_LOCATION = 299
    }

    private val TAG = "LocationService"
    private var isLocationGranted: Boolean = false
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var location: Location? = null

    init {
        activity?.let {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(it)
            getLocationPermission()
        }
    }

    interface CallBack {
        fun onLocationAvailable(location: Location?)
        fun onLocationDenied()
    }

    private fun getLocationPermission() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                isLocationGranted = true
                getDeviceLocation()
            } else {
                requestLocationPermission()
            }
        }
    }

    private fun getDeviceLocation() {
        try {
            if (isLocationGranted) {
                fusedLocationProviderClient.locationAvailability.addOnSuccessListener { locationAvailability ->
                    if (locationAvailability.isLocationAvailable) {
                        activity?.let {
                            val locationTask = fusedLocationProviderClient.lastLocation

                            locationTask.addOnCompleteListener { task ->
                                location = task.result

                                location?.let {
                                    fusedLocationProviderClient.flushLocations()
                                        .addOnCompleteListener {
                                            callBack?.onLocationAvailable(location)
                                        }
                                }
                            }
                        }
                    } else {
                        callBack?.onLocationDenied()
                    }
                }
            } else {
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            callBack?.onLocationDenied()
            Log.e(TAG, e.localizedMessage ?: e.stackTraceToString())
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        isLocationGranted = false
        when (requestCode) {
            ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    isLocationGranted = true
                    showEnableLocationSetting()
                } else {
                    callBack?.onLocationDenied()
                }
            }
        }
    }

    private fun requestLocationPermission() {
        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                ACCESS_FINE_LOCATION
            )
        }
    }

    private fun showEnableLocationSetting() {
        activity?.let {
            val locationRequest = getLocationRequest()

            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

            val task = LocationServices.getSettingsClient(it)
                .checkLocationSettings(builder.build())

            task.addOnSuccessListener { response ->
                val states = response.locationSettingsStates
                if (states.isLocationPresent) {
                    getLocationPermission()
                }
            }

            task.addOnFailureListener { e ->
                if (e is ResolvableApiException) {
                    try {
                        e.startResolutionForResult(
                            it,
                            ACCESS_FINE_LOCATION
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                    }
                }
            }
        }
    }

    private fun getLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.fastestInterval = 1000
        locationRequest.interval = 2000

        return locationRequest
    }

    fun onActivityResult(requestCode: Int, resultCode: Int) {
        when (requestCode) {
            ACCESS_FINE_LOCATION -> when (resultCode) {
                Activity.RESULT_OK -> {
                    getLocationPermission()
                }
                Activity.RESULT_CANCELED -> {
                    if (LocationManagerCompat.isLocationEnabled(activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager)) {
                        getLocationPermission()
                    } else {
                        callBack?.onLocationDenied()
                    }
                }
            }
        }
    }
}