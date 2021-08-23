package no.hiof.larseknu.playingwithandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    private var LOGTAG = "PlayingWithAndroid"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(LOGTAG, "onCreate() ran")
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOGTAG, "onStart() ran")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOGTAG, "onResume() ran")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOGTAG, "onPause() ran")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOGTAG, "onStop() ran")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(LOGTAG, "onRestart() ran")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOGTAG, "onDestroy() ran")
    }

    fun navigateToOtherActivity(view: View) {
        var intent = Intent(this, OtherActivity::class.java)
        startActivity(intent)
    }
}
