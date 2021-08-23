package no.hiof.larseknu.playingwithservices

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class BoundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bound)
        supportActionBar?.title = "BoundActivity"
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    fun getLocation(view: View) {

    }
}