package no.hiof.larseknu.playingwithservices.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import no.hiof.larseknu.playingwithservices.LocationWorker;
import no.hiof.larseknu.playingwithservices.MainActivity;

public class MyForegroundService extends Service {

    private static final String LOGTAG = MyForegroundService.class.getSimpleName();

    private LocationWorker locationWorker;

    private ExecutorService executorService;

    @Override
    public void onCreate() {
        super.onCreate();

        locationWorker = new LocationWorker(this);
        executorService = Executors.newFixedThreadPool(3);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOGTAG, "onStartCommand Thread: " + Thread.currentThread().getName());

        // Create an intent for the notification to bring back to MainActivity if clicked
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // Create the notification using the notification builder
        Notification notification =
                new NotificationCompat.Builder(getApplicationContext(), MainActivity.NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.btn_star)
                        .setContentTitle("Notification Title")
                        .setContentText("Text")
                        .setContentIntent(pendingIntent)
                        .build();


        startForeground(123, notification);

        MyForegroundService.ServiceRunnable runnable = new MyForegroundService.ServiceRunnable();
        executorService.execute(runnable);

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(LOGTAG, "onDestroy Thread: " + Thread.currentThread().getName());
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class ServiceRunnable implements Runnable {
        @Override
        public void run() {
            Log.i(LOGTAG, "MyForegroundService.ServiceRunnable Thread: " + Thread.currentThread().getName());
            try {
                Location location = locationWorker.getLocation();

                String address = locationWorker.reverseGeocode(location);

                JSONObject jsonObject = locationWorker.getJSONObjectFromURL("https://www.omdbapi.com/?i=tt3896198&apikey=2f6990a0");

                locationWorker.saveToFile(location, address, jsonObject.getString("Title"), "MyStartedService.txt");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            stopForeground(true);
        }
    }
}
