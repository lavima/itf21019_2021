package no.hiof.larseknu.playingwithservices;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import no.hiof.larseknu.playingwithservices.service.MyForegroundService;
import no.hiof.larseknu.playingwithservices.service.MyIntentService;
import no.hiof.larseknu.playingwithservices.service.MyJobIntentService;
import no.hiof.larseknu.playingwithservices.service.MyStartedService;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static no.hiof.larseknu.playingwithservices.MyWorker.WORKER_FILENAME;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private TextView resultTextView;
    private static final int PERMISSION_ID = 12;

    private String[] neededPermissions = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
        createNotificationChannel();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void startStartedService(View view) {
        startStartedService_();
    }

    //@AfterPermissionGranted(PERMISSION_ID)
    private void startStartedService_() {
        if (EasyPermissions.hasPermissions(this, neededPermissions)) {
            startService(new Intent(this, MyStartedService.class));
        }
        else {
            EasyPermissions.requestPermissions(this, "We need permission", PERMISSION_ID, neededPermissions);
        }
    }

    public void stopStartedService(View view) {
        stopService(new Intent(this, MyStartedService.class));
    }

    public void scheduleJob(View view) {
        scheduleJob_();
    }

    //@AfterPermissionGranted(PERMISSION_ID)
    private void scheduleJob_() {
        if (EasyPermissions.hasPermissions(this, neededPermissions)) {
            Constraints constraints = new Constraints.Builder()
                    .setRequiresCharging(true)
                    .build();

            Data fileName = new Data.Builder().putString(WORKER_FILENAME, "MyWorker.txt").build();

            OneTimeWorkRequest saveFileWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInitialDelay(5, TimeUnit.SECONDS)
                    .setInputData(fileName)
                    .setConstraints(constraints)
                    .build();

            WorkManager.getInstance(this).enqueue(saveFileWorkRequest);

            Log.i("MainActivity", "MyWorker - Called");
        }
        else {
            EasyPermissions.requestPermissions(this, "We need permission", PERMISSION_ID, neededPermissions);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyStartedService.class));
    }


    public void startForegroundService(View view) {
        startForegroundService_();
    }

    //@AfterPermissionGranted(PERMISSION_ID)
    private void startForegroundService_() {
        if (EasyPermissions.hasPermissions(this, neededPermissions)) {
            Log.i("startForegroundService", "Thread: " + Thread.currentThread().getName());
            startForegroundService(new Intent(this, MyForegroundService.class));
        }
        else {
            EasyPermissions.requestPermissions(this, "We need permission", PERMISSION_ID, neededPermissions);
        }
    }

    public void saveAddressIntentService(View view) {
        saveAddressIntentService_();
    }

    //@AfterPermissionGranted(PERMISSION_ID)
    private void saveAddressIntentService_() {
        if (EasyPermissions.hasPermissions(this, neededPermissions)) {
            // This uses IntentService with ResultReceiver
            // MyIntentService.startActionRetreiveAndSaveAddress(this, "MyIntentService.txt", new MyResultReceiver(null));
            // This uses JobIntentService
            MyJobIntentService.enqueueWork(this, "MyJobIntentService.txt", new MyResultReceiver(null));
        }
        else {
            EasyPermissions.requestPermissions(this, "We need permission", PERMISSION_ID, neededPermissions);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        // Add logic here if you want the operation that was put on hold to run
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    private class MyResultReceiver extends ResultReceiver {
        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            Log.i("MyResultReceiver", "Thread: " + Thread.currentThread().getName());

            if (resultCode == MyIntentService.RESULT_CODE && resultData != null) {
                final String result = resultData.getString(MyIntentService.RESULT_DATA_KEY);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("MyHandler", "Thread: " + Thread.currentThread().getName());

                        resultTextView.setText(result);
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goToBoundActivity:
                startActivity(new Intent(this, BoundActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "test_channel";
            String description = "Test Channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(name.toString(), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
