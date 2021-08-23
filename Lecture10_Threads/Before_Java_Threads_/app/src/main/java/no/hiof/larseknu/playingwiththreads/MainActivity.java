package no.hiof.larseknu.playingwiththreads;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import com.google.android.material.snackbar.Snackbar;

import android.text.style.EasyEditSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private TextView statusText;

    private final String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION};
    private final int PERMISSION_STORAGE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);

        StrictMode.enableDefaults();
    }

    public void doWork(View view) {
        Worker worker = new Worker(this);

        statusText.setText("Starting");

        //JSONObject object = worker.getJSONObjectFromURL("http://www.omdbapi.com/?i=tt3896198&apikey=33f85a4c");
        statusText.setText("Received JSON");

        Location location = worker.getLocation();
        statusText.setText("Received location");

        String address = worker.reverseGeocode(location);
        statusText.setText("Received address");

        worker.saveToFile(location, address, "Test", "DataFile.txt");
        statusText.setText("Saved data to file");

    }

    public void doWorkInThread(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Worker worker = new Worker(MainActivity.this);

                updateStatusText("Starting");

                JSONObject object = worker.getJSONObjectFromURL("http://www.omdbapi.com/?i=tt3896198&apikey=33f85a4c");
                updateStatusText("Received JSON");

                Location location = worker.getLocation();
                updateStatusText("Received location");

                String address = worker.reverseGeocode(location);
                updateStatusText("Received address");

                try {
                    worker.saveToFile(location, address, object.getString("Title"), "ThreadDataFile.txt");
                }
                catch (JSONException exception) {
                    exception.printStackTrace();
                }

                updateStatusText("Saved data to file");
            }
        });
        thread.start();
    }

    public void doWorkAsync(View view) {
        doWorkWithPermissions();
    }

    @AfterPermissionGranted(PERMISSION_STORAGE_LOCATION)
    public void doWorkWithPermissions() {

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            DoWorkAsync workAsync = new DoWorkAsync(this, statusText);
//            workAsync.execute();
//        }
//        else if (shouldShowRequestPermissionRationale(Manifest.permission.INTERNET)) {
//
//        }
//        else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
//
//        }
//        else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//        }
//        else {
//            // You can directly ask for the permission.
//            requestPermissions(new String[] { Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE },
//                    101);
//        }

        if (EasyPermissions.hasPermissions(this, permissions)) {
            DoWorkAsync workAsync = new DoWorkAsync(this, statusText);
            workAsync.execute();
        }
        else {
            EasyPermissions.requestPermissions(this, "Location and storage needed", PERMISSION_STORAGE_LOCATION, permissions);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode != 101)
//            return;
//
//        if (grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
//            Log.d("PermissionRequest", "Granted");
//            doWorkAsync(statusText);
//        }
//        else {
//            Log.d("PermissionRequest", "Not Granted");
//            //new AppSettingsDialog.Builder(this).build().show();
//            Snackbar.make(findViewById(R.id.layout), "We need access to ...", Snackbar.LENGTH_LONG)
//                    .setAction("Settings", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                            intent.setData(Uri.fromParts("package", getPackageName(), null));
//                            startActivity(intent);
//                        }
//                    })
//                    .show();
//        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    public void updateStatusText(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statusText.setText(message);
            }
        });
    }
}
