package no.hiof.larseknu.playingwithintents

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEY_NAME = "name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startOtherActivityButton.setOnClickListener { view ->
            val intent = Intent(view.context, OtherActivity::class.java)

            val name = nameEditText.text.toString()

            intent.putExtra(KEY_NAME, name)

            startActivity(intent)
        }
    }

    fun openWebPage(view: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://hiof.no"))

        startActivity(intent)
    }

    fun openMaps(view: View) {
        //Uri uri = Uri.parse("https://maps.google.com?q=59.128708,11.353176");

        val uri = Uri.parse("geo:59.128708,11.353176")

        val intent = Intent(Intent.ACTION_VIEW, uri)

        startActivity(intent)
    }
}

