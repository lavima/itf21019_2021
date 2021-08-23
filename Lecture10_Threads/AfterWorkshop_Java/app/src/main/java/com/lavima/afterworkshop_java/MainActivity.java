package com.lavima.afterworkshop_java;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView titleText;
    ImageView posterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = findViewById(R.id.titleText);
        posterImage = findViewById(R.id.posterImage);

        //GetMovieAsync getMovieAsync = new GetMovieAsync(titleText, posterImage);
        //getMovieAsync.execute("https://www.omdbapi.com/?i=tt3896198&apikey=2f6990a0");

        Thread getMoviewThred = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL("https://www.omdbapi.com/?i=tt3896198&apikey=2f6990a0");

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();
                    urlConnection.disconnect();

                    String jsonString = sb.toString();
                    JSONObject jsonObject = new JSONObject(jsonString);

                    InputStream in = new java.net.URL(jsonObject.getString("Poster")).openStream();
                    Bitmap poster = BitmapFactory.decodeStream(in);

                    updateUI(jsonObject.getString("Title"), poster);
                }
                catch (JSONException jsone) {
                    Log.e("Worker.getJSON", jsone.getMessage());
                }
                catch (IOException ioe) {
                    Log.e("Worker.getJSON", ioe.getMessage());
                }
            }
        });
        getMoviewThred.start();
    }

    private void updateUI(final String title, final Bitmap poster) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                titleText.setText(title);
                posterImage.setImageBitmap(poster);
            }
        });
    }

}