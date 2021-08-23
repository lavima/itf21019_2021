package no.hiof.larseknu.playingwithservices.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import no.hiof.larseknu.playingwithservices.Worker


const val ACTION_RETREIVE_AND_SAVE_ADDRESS = "no.hiof.larseknu.playingwithservices.service.action.ADDRESS"

const val EXTRA_FILENAME = "no.hiof.larseknu.playingwithservices.service.extra.FILENAME"


class MyIntentService : IntentService("MyIntentService") {
    private val LOGTAG = MyIntentService::class.java.simpleName

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_RETREIVE_AND_SAVE_ADDRESS -> {
                val fileName = intent.getStringExtra(EXTRA_FILENAME)
                handleActionSaveAddress(fileName)
            }
        }
    }

    private fun handleActionSaveAddress(fileName: String) {
        val worker = Worker(applicationContext)
        Log.d(LOGTAG, "Worker Started " + Thread.currentThread().name)

        val location = worker.retrieveLocation()
        Log.d(LOGTAG, "Got location")

        val address = worker.reverseGeocode(location)
        Log.d(LOGTAG, "Got address")

        val json = worker.getJSONObjectFromURL("http://www.omdbapi.com/?i=tt3896198&apikey=2f6990a0")
        Log.d(LOGTAG, "Got JSON")

        worker.saveToFile(location, address, json.getString("Title"), fileName)
        Log.d(LOGTAG, "Saved file")

        Log.d(LOGTAG, "MyIntentService Done")
    }
}
