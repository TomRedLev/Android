package com.example.stackapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button draw = findViewById(R.id.stopButton);
        draw.setOnClickListener( (v) -> {
            startActivity(new Intent(this, DrawActivity.class));
        });

        Button write = findViewById(R.id.playButton);
        write.setOnClickListener( (v) -> {
            startActivity(new Intent(this, WriteActivity.class));
        });

        Button hs = findViewById(R.id.hyperstackButton);
        hs.setOnClickListener( (v) -> {
            startActivity(new Intent(this, HyperstackActivity.class));
        });

        Button im = findViewById(R.id.recordButton);
        im.setOnClickListener( (v) -> {
            startActivity(new Intent(this, ImageActivity.class));
        });

        Button audio = findViewById(R.id.audioButton);
        audio.setOnClickListener( (v) -> {
            startActivity(new Intent(this, AudioActivity.class));
        });
    }
}