package no.hiof.larseknu.playingwiththreads

import android.Manifest
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    companion object {
        const val MY_PERMISSIONS_ACCESS_LOCATION_AND_STORAGE = 1

        val neededPermissions : Array<String> = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_permission)
        supportActionBar?.setTitle("EasyPermissions")
    }


    fun doAsyncWork(view: View) {
        retrieveAndSaveAddress()
    }

    @AfterPermissionGranted(MY_PERMISSIONS_ACCESS_LOCATION_AND_STORAGE)
    private fun retrieveAndSaveAddress() {
         val asyncTaskWorker = AsyncTaskWorker()

        if (EasyPermissions.hasPermissions(this, *neededPermissions))
            asyncTaskWorker.execute()
        else
            EasyPermissions.requestPermissions(
                this,
                "Location and local storage access needed to be able to save a local address",
                MY_PERMISSIONS_ACCESS_LOCATION_AND_STORAGE, *neededPermissions
            )

    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.d("EasyPermission", "Permissions granted")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    inner class AsyncTaskWorker : AsyncTask<Void, String, Boolean>() {
        override fun doInBackground(vararg voids: Void): Boolean? {
            val worker = Worker(this@MainActivity)
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
