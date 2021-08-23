package com.lavima.afterworkshop_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends FragmentActivity {

    public boolean IsDualView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if (savedInstanceState != null)
        //    return;

        // If fragment in main container does not already exists (this is to avoid recreation)
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            MainFragment mainFragment = new MainFragment();
            // Begin new transaction, add fragment, and commit
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainFragment).commit();
        }

        // Check if we are in landscape orientation (the secondary fragment container should then be available)
        View fragmentContainer = findViewById(R.id.fragment_container2);
        if (fragmentContainer != null) {

            // Set the public flag to indicate whether or not we are in dual view
            IsDualView = true;
            // If fragment in secondary container does not already exists (this is to avoid recreation)
            if (getSupportFragmentManager().findFragmentById(R.id.fragment_container2) == null) {
                OtherFragment otherFragment = new OtherFragment();
                // Begin new transaction, add fragment, and commit
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container2, otherFragment).commit();
            }
        }
        else
            // Set the public flag to indicate whether or not we are in dual view
            IsDualView = false;

    }


}