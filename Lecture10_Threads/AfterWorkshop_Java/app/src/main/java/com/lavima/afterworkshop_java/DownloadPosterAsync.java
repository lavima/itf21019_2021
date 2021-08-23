package com.lavima.afterworkshop_java;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadPosterAsync extends AsyncTask<String, Void, Bitmap> {

    ImageView posterImage;

    public DownloadPosterAsync(ImageView posterImage) {
        this.posterImage = posterImage;
    }
    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap bmp = null;
        try {
            InputStream in = new java.net.URL(urls[0]).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("DownloadPosterAsync", e.getMessage());
            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        posterImage.setImageBitmap(bitmap);
    }
}
