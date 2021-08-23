package no.hiof.larseknu.playingwiththreads

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException

class MainActivityWithoutPermissions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        StrictMode.enableDefaults()
    }

    fun doWorkThreadless(view: View) {
        val worker = Worker(this)
        statusText.text = "Starting"

        val location = worker.retrieveLocation()
        statusText.text = "Retrieved Location"

        val address = worker.reverseGeocode(location)
        statusText.text = "Retrieved Address"

        // Not allowed to do network-requests directly on the main thread
        //val json = worker.getJSONObjectFromURL("http://www.omdbapi.com/?i=tt3896198&apikey=33f85a4c")
        //statusText.text = "Retrieved JSON"

        //worker.saveToFile(location, address, json.getString("Title"), "NonThreadFile.txt")
        //worker.saveToFile(location, address, "MovieTitle", "NonThreadFile.txt")

        statusText.text = "Done"
    }

    fun doWorkInThread(view: View) {
        val workerThread = Thread(Runnable {
            val worker = Worker(this@MainActivityWithoutPermissions)
            updateUI("Starting")

            val jsonObject = worker.getJSONObjectFromURL("http://www.omdbapi.com/?i=tt3896198&apikey=33f85a4c")
            updateUI("Retrieved JSON")

            val location = worker.retrieveLocation()
            updateUI("Retrieved Location")

            val address = worker.reverseGeocode(location)
            updateUI("Retrieved Address")

            worker.saveToFile(location, address, jsonObject.getString("Title"), "ThreadFile.txt")

            updateUI("Done")
        })

        workerThread.start()
    }

    fun updateUI(message: String) {
        runOnUiThread { statusText.text = message }
    }

    fun doWorkInAsyncTask(view: View) {
        val asyncTaskWorker = AsyncTaskWorker()
        asyncTaskWorker.execute()
    }

    inner class AsyncTaskWorker : AsyncTask<Void, String, Boolean>() {
        override fun doInBackground(vararg voids: Void): Boolean? {
            val worker = Worker(this@MainActivityWithoutPermissions)
            publishProgress("Starting")

            val jsonObject =
                worker.getJSONObjectFromURL("http://www.omdbapi.com/?i=tt3896198&apikey=33f85a4c")
            publishProgress("Retrieved JSON")

            val location = worker.retrieveLocation()
            publishProgress("Retrieved Location")

            val address = worker.reverseGeocode(location)
            publishProgress("Retrieved Address")

            worker.saveToFile(location, address, jsonObject.getString("Title"), "AsyncTaskFile.txt")

            publishProgress("Done")

            return true
        }

        override fun onProgressUpdate(vararg messages: String) {
            statusText.text = messages[0]
        }

        override fun onPostExecute(result: Boolean) {
            if (result)
                statusText.text = "Done"
            else
                statusText.text = "Something failed =("
        }
    }
}
