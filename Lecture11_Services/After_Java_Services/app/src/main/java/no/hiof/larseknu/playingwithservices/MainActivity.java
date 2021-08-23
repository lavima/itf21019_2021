package no.hiof.larseknu.playingwithservices;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import no.hiof.larseknu.playingwithservices.service.MyIntentService;
import no.hiof.larseknu.playingwithservices.service.MyStartedService;

import static no.hiof.larseknu.playingwithservices.MyWorker.WORKER_FILENAME;

public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
    }

    public void startStartedService(View view) {
        startService(new Intent(this, MyStartedService.class));
    }

    public void stopStartedService(View view) {
        stopService(new Intent(this, MyStartedService.class));
    }

    public void scheduleJob(View view) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyStartedService.class));
    }

    public void saveAddressIntentService(View view) {
        MyIntentService.startActionRetreiveAndSaveAddress(this, "MyIntentService.txt", new MyResultReceiver(null));
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
}
