package no.hiof.larseknu.playingwithservices.service

import android.app.Service
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import no.hiof.larseknu.playingwithservices.Worker
import java.util.*

class MyBoundService : Service() {
    private val myLocalBinder = MyLocalBinder()
    private var currentLocation: Location = createLocationManually()

    private lateinit var worker: Worker

    override fun onCreate() {
        super.onCreate()
        worker = Worker(this)
        worker.monitorGpsInBackground(MyLocationListener())
    }

    override fun onDestroy() {
        super.onDestroy()
        worker.stopGpsMonitoring()
    }

    inner class MyLocalBinder : Binder() {
        fun getService() : MyBoundService = this@MyBoundService
    }

    override fun onBind(intent: Intent): IBinder? {
        return myLocalBinder
    }

    fun getCurrentLocation(): Location {
        return currentLocation
    }

    internal inner class MyLocationListener : LocationListener {

        override fun onLocationChanged(location: Location) {
            currentLocation = location
        }

        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}

        override fun onProviderEnabled(s: String) {}

        override fun onProviderDisabled(s: String) {}
    }

    private fun createLocationManually(): Location {
        val lastLocation = Location("Hiof")
        val now = Date()
        lastLocation.time = now.time
        lastLocation.latitude = 59.128229
        lastLocation.longitude = 11.352860

        return lastLocation
    }
}