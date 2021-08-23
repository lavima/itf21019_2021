package no.hiof.larseknu.playingwithintents

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_other.*

class OtherActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_GET: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)

        val name = intent.getStringExtra(MainActivity.KEY_NAME)

        textView.text = name
    }

    fun getPicture(view: View){
        Toast.makeText(this, "Getting picture", Toast.LENGTH_SHORT).show();

        val intent = Intent(Intent.ACTION_GET_CONTENT)

        intent.type = "image/*"

        startActivityForResult(intent, REQUEST_IMAGE_GET)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == REQUEST_IMAGE_GET && resultCode == Activity.RESULT_OK && intent != null) {
            val picture = MediaStore.Images.Media.getBitmap(contentResolver, intent.data)

            imageView.setImageBitmap(picture)
        }

        super.onActivityResult(requestCode, resultCode, intent)
    }
}
