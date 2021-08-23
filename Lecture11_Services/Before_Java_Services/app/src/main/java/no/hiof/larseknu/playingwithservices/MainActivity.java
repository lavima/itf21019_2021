package no.hiof.larseknu.playingwithservices;


import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
    }

    public void scheduleJob(View view) {
    }

    public void startStartedService(View view) {
    }

    public void stopStartedService(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void saveAddressIntentService(View view) {
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
