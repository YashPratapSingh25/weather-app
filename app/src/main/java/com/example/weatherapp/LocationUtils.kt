package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationUtils (private val _context : Context) {

    fun hasPermission(permission : String) : Boolean {
        return (ContextCompat.checkSelfPermission(_context, permission) == PackageManager.PERMISSION_GRANTED)
    }

    private val _fusedLocationClient : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(_context)

    @SuppressLint("MissingPermission")
    fun getLocation(viewModel: MainViewModel){
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                p0.lastLocation ?. let {
                    val newLocation = LocationData(it.latitude, it.longitude)
                    viewModel.updateLocation(newLocation)
                }
            }
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1000
        ).build()

        _fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    fun reverseGeocoderLocation(location : LocationData) : String{
        val geocoder = Geocoder(_context)
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        return if(addresses ?. isNotEmpty() == true){
            addresses[0].getAddressLine(0)
        }else{
            "No location found"
        }
    }

}