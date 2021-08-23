package com.lavima.afterworkshop_java;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetMovieAsync extends AsyncTask<String, Void, JSONObject> {

    private TextView titleText;
    private ImageView posterImage;

    public GetMovieAsync(TextView titleText, ImageView posterImage) {
        this.titleText = titleText;
        this.posterImage = posterImage;
    }

    private JSONObject getJSONObjectFromURL(String urlString)  {

        try {
            URL url = new URL(urlString);

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

            return new JSONObject(jsonString);
        }
        catch (JSONException jsone) {
            Log.e("Worker.getJSON", jsone.getMessage());
        }
        catch (IOException ioe) {
            Log.e("Worker.getJSON", ioe.getMessage());
        }

        return null;

    }

    @Override
    protected JSONObject doInBackground(String... urlStrings) {
        return getJSONObjectFromURL(urlStrings[0]);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        if (jsonObject == null)
            return;

        try {
            titleText.setText(jsonObject.getString("Title"));
            DownloadPosterAsync downloadPosterAsync = new DownloadPosterAsync(posterImage);
            downloadPosterAsync.execute(jsonObject.getString("Poster"));
        }
        catch (JSONException e) {
            titleText.setText(e.getMessage());
        }
    }
}
