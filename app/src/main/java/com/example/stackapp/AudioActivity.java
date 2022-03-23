package com.example.stackapp;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

public class AudioActivity extends AppCompatActivity {

    MediaRecorder mr;
    MediaPlayer mp;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {

            } else {
                String[] perm = {Manifest.permission.RECORD_AUDIO};
                requestPermissions(perm, 200);
            }
        } else {
            Toast.makeText(this, "No microphone", Toast.LENGTH_LONG);
            return ;
        }

        Button record = findViewById(R.id.recordButton);
        record.setOnClickListener( (v) -> {
            mr = new MediaRecorder();
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);
            mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mr.setOutputFile(getFilePath());
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            try {
                mr.prepare();
                Toast.makeText(this, "Start recording", Toast.LENGTH_LONG);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mr.start();
        });

        Button stop = findViewById(R.id.playButton);
        stop.setOnClickListener( (v) -> {
            mr.stop();
            mr.release();
            mr = null;
            Toast.makeText(this, "Stop recording", Toast.LENGTH_LONG);
        });

        Button play = findViewById(R.id.playButton);
        play.setOnClickListener( (v) -> {
            mp = new MediaPlayer();
            try {
                mp.setDataSource(getFilePath());
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.start();
        });
    }

    private String getFilePath() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File dir = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File f = new File(dir, "record.mp3");
        return f.getPath();
    }
}