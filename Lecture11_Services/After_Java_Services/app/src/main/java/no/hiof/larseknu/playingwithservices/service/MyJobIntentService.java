package no.hiof.larseknu.playingwithservices.service;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import org.json.JSONException;
import org.json.JSONObject;

import no.hiof.larseknu.playingwithservices.LocationWorker;

public class MyJobIntentService extends JobIntentService {

    private static final String LOGTAG = MyJobIntentService.class.getSimpleName();

    // The following isn't needed but examplify Inter Process Communication (IPC)

    private static final String EXTRA_FILENAME = "no.hiof.larseknu.playingwithservices.service.extra.FILENAME";
    private static final String EXTRA_RESULT_RECEIVER = "no.hiof.larseknu.playingwithservices.service.extra.RESULT_RECEIVER";

    public static final int RESULT_CODE = 1;
    public static final String RESULT_DATA_KEY = "no.hiof.larseknu.playingwithservices.intentservice.RESULT_DATA";

    public static void enqueueWork(Context context, String fileName, ResultReceiver resultReceiver) {
        Intent intent = new Intent(context, MyJobIntentService.class);
        intent.putExtra(EXTRA_FILENAME, fileName);
        intent.putExtra(EXTRA_RESULT_RECEIVER, resultReceiver);
        enqueueWork(context, MyJobIntentService.class, 123, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        Log.i(LOGTAG, "Intent received + Thread:" + Thread.currentThread().getName());

        String fileName = intent.getStringExtra(EXTRA_FILENAME);
        ResultReceiver resultReceiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
        try {

            LocationWorker locationWorker = new LocationWorker(getApplicationContext());
            Log.d(LOGTAG, "LocationWorker Started");

            Location location = locationWorker.getLocation();
            Log.d(LOGTAG, "Got location");

            String address = locationWorker.reverseGeocode(location);
            Log.d(LOGTAG, "Got address");

            JSONObject json = locationWorker.getJSONObjectFromURL("https://www.omdbapi.com/?i=tt3896198&apikey=2f6990a0");
            Log.d(LOGTAG, "Got JSON");

            locationWorker.saveToFile(location, address, json.getString("Title"), fileName);
            Log.d(LOGTAG, "Saved file");

            Log.d(LOGTAG, "MyIntentService Done");

            Bundle bundle = new Bundle();
            bundle.putString(RESULT_DATA_KEY, "IntentService done");
            resultReceiver.send(RESULT_CODE, bundle);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(LOGTAG, "Work finished");

    }
}
