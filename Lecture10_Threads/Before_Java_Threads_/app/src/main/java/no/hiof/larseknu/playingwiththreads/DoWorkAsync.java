package no.hiof.larseknu.playingwiththreads;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DoWorkAsync extends AsyncTask<Void, String, Void> {
    private Context context;
    private TextView statusText;

    public DoWorkAsync(Context context, TextView statusText) {
        this.context = context;
        this.statusText = statusText;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        Worker worker = new Worker(context);

        publishProgress("Starting");

        JSONObject object = worker.getJSONObjectFromURL("http://www.omdbapi.com/?i=tt3896198&apikey=33f85a4c");
        publishProgress("Received JSON");

        Location location = worker.getLocation();
        publishProgress("Received location");

        String address = worker.reverseGeocode(location);
        publishProgress("Received address");

        try {
            worker.saveToFile(location, address, object.getString("Title"), "ThreadDataFile.txt");
        }
        catch (JSONException exception) {
            exception.printStackTrace();
        }

        publishProgress("Saved data to file");

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        statusText.setText(values[0]);
    }
}
