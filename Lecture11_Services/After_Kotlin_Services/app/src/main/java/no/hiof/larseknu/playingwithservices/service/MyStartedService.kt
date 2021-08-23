package no.hiof.larseknu.playingwithservices.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import no.hiof.larseknu.playingwithservices.Worker
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MyStartedService : Service() {
    private val LOGTAG = MyStartedService::class.java.simpleName

    private lateinit var worker : Worker

    private lateinit var executorService: ExecutorService
    private lateinit var scheduledExecutorService: ScheduledExecutorService

    override fun onCreate() {
        Log.i("MyStartedService", "MyStartedService.onCreate Thread:" + Thread.currentThread().name)

        worker = Worker(this)
        worker.monitorGpsInBackground()

        executorService = Executors.newFixedThreadPool(3)
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(LOGTAG, "MyStartedService.onStartCommand Thread: " + Thread.currentThread().name)

        executorService.execute {
            Log.i(LOGTAG, "MyStartedService.Executor Thread: " + Thread.currentThread().name + " StartId: " + startId)
            val location = worker.retrieveLocation()

            val address = worker.reverseGeocode(location)

            val jsonObject = worker.getJSONObjectFromURL("http://www.omdbapi.com/?i=tt3896198&apikey=2f6990a0")

            worker.saveToFile(location, address, jsonObject.getString("Title"), "MyStartedService.txt")
            Log.i(LOGTAG, "MyStartedService.Executor Thread: " + Thread.currentThread().name + " StartId: " + startId + " - DONE")

            scheduledExecutorService.schedule({
                stopSelfResult(startId)
            }, 10, TimeUnit.SECONDS)
        }

        return START_STICKY
    }

    override fun onDestroy() {
        Log.i(LOGTAG, "MyStartedService.onDestroy Thread: " + Thread.currentThread().name)
        worker.stopGpsMonitoring()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
