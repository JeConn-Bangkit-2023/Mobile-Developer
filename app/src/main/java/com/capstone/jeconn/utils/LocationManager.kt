package com.capstone.jeconn.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.capstone.jeconn.data.entities.LocationEntity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationManagers(
    context: Context,
    private var timeInterval: Long,
    private var minimalDistance: Float,
) : LocationCallback(){

    private var request: LocationRequest
    private var locationClient: FusedLocationProviderClient

    private var location: Location? = null

    init {
        // getting the location client
        locationClient = LocationServices.getFusedLocationProviderClient(context)
        request = createRequest()
    }

    private fun createRequest(): LocationRequest =
        // New builder
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, timeInterval).apply {
            setMinUpdateDistanceMeters(minimalDistance)
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()

    fun changeRequest(timeInterval: Long, minimalDistance: Float, context: Context, activity: Activity, myLocation: Location) {
        this.timeInterval = timeInterval
        this.minimalDistance = minimalDistance
        createRequest()
        stopLocationTracking()
        startLocationTracking(context = context, activity, myLocation)
    }

    fun startLocationTracking(context: Context, activity: Activity, myLocation: Location) {
        this.location = myLocation
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission(activity)
        } else {
            locationClient.requestLocationUpdates(request, this, Looper.getMainLooper())
        }
    }

    private fun requestLocationPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            99
        )
    }


    fun stopLocationTracking() {
        locationClient.flushLocations()
        locationClient.removeLocationUpdates(this)
    }

    override fun onLocationResult(location: LocationResult) {
        for (i in  location.locations) {
            this.location?.reloadLocation(
                LocationEntity(
                    longitude = i.longitude,
                    latitude = i.latitude
                )
            )
        }
    }

    override fun onLocationAvailability(availability: LocationAvailability) {
        Log.e("availability", availability.isLocationAvailable.toString())
    }
}

interface Location {
    fun reloadLocation(location: LocationEntity)
}