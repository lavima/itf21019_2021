package no.hiof.larseknu.playingwithservices

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

const val WORKER_FILENAME = "no.hiof.larseknu.playingwithservices.myworker.FILENAME"

class MyWorker(private val appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    private val LOGTAG = MyWorker::class.java.simpleName

    override fun doWork(): Result {
        val fileName = inputData.getString(WORKER_FILENAME)

        val worker = Worker(appContext)
        Log.d(LOGTAG, "MyWorker Started " + Thread.currentThread().name)

        val location = worker.retrieveLocation()
        Log.d(LOGTAG, "Got location")

        val address = worker.reverseGeocode(location)
        Log.d(LOGTAG, "Got address")

        val json = worker.getJSONObjectFromURL("http://www.omdbapi.com/?i=tt2096673&apikey=2f6990a0")
        Log.d(LOGTAG, "Got JSON")

        worker.saveToFile(location, address, json.getString("Title"), fileName!!)
        Log.d(LOGTAG, "Saved file")

        Log.d(LOGTAG, "MyWorker Done")

        // Indicate whether the task finished successfully with the Result
        return Result.success()
    }

}