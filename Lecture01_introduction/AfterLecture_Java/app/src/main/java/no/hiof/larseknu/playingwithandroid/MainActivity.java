package no.hiof.larseknu.playingwithandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private String LOG_TAG = "PlayingWithAndroid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "onCreate() ran");
    }

    // Override the onStart() ActivityLifecycle event and add logging (Ctrl + O - Override shortcut)
    @Override
    protected void onStart() {
        super.onStart();

        Log.d(LOG_TAG, "onStart() ran");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "onResume() ran");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(LOG_TAG, "onPause() ran");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop() ran");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(LOG_TAG, "onRestart() ran");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(LOG_TAG, "onDestroy() ran");
    }

    //Done: Create OtherActivity.java with the "New->Activity->Empty activity" dropdown menu
    //Done: Add a TextView to OtherActivity with the text "Hello Android!"

    // Add a navigateToOtherActivity(View view), method for a button-click
    public void navigateToOtherActivity(View view) {
        //Create an Intent to start OtherActivity in the navigate... method
        Intent intent = new Intent(this, OtherActivity.class);

        //Start OtherActivity with the intent
        startActivity(intent);
    }

}
