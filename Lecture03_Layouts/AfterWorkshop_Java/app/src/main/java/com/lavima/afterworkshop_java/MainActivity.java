package com.lavima.afterworkshop_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLong = findViewById(R.id.buttonLong);
        Button buttonMulti = findViewById(R.id.buttonMulti);
        Button buttonFrame = findViewById(R.id.buttonFrame);

        buttonLong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ScrollLongActivity.class));
            }
        });
        buttonMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ScrollMultiActivity.class));
            }
        });
        buttonFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), FrameActivity.class));
            }
        });
    }
}