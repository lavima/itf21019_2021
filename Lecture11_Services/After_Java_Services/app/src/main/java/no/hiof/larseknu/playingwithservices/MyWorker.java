package no.hiof.larseknu.playingwithservices;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import androidx.work.Worker;

import org.json.JSONException;
import org.json.JSONObject;

public class MyWorker extends Worker {
    public static String WORKER_FILENAME = "no.hiof.larseknu.playingwithservices.myworker.FILENAME";
    private final String LOGTAG = MyWorker.class.getSimpleName();
    private Context context;

    public MyWorker(Context context, WorkerParameters params) {
        super(context, params);

        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        String fileName = getInputData().getString(WORKER_FILENAME);

        LocationWorker worker = new LocationWorker(context);
        Log.d(LOGTAG, "MyWorker Started " + Thread.currentThread().getName());

        Location location = worker.getLocation();
        Log.d(LOGTAG, "Got location");

        String address = worker.reverseGeocode(location);
        Log.d(LOGTAG, "Got address");

        JSONObject json = worker.getJSONObjectFromURL("http://www.omdbapi.com/?i=tt2096673&apikey=2f6990a0");
        Log.d(LOGTAG, "Got JSON");

        try {
            worker.saveToFile(location, address, json.getString("Title"), fileName);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(LOGTAG, "Saved file");

        Log.d(LOGTAG, "MyWorker Done");

        // Indicate whether the task finished successfully with the Result
        return Result.success();
    }
}
