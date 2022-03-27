package com.example.stackapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;

public class AudioActivity extends AppCompatActivity {

    MediaRecorder mr;
    MediaPlayer mp;

    private void checkPermission() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    1001);
        } else {
            Toast.makeText(this, "No microphone", Toast.LENGTH_LONG);
            return ;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        checkPermission();

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        String[] pictureDialogItems = {"Select audio from gallery", "Record audio from microphone"};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0: retrievePhotoFromGallery(); break;
                        case 1: recordAudio(); break;
                    }});
        pictureDialog.show();
    }

    private void recordAudio() {
        Button record = findViewById(R.id.recordButton);
        record.setOnClickListener( (v) -> {
            Toast.makeText(this, "Start recording", Toast.LENGTH_LONG);
            mr = new MediaRecorder();
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);
            mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mr.setOutputFile(getFilePath());
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            try {
                Log.v("Record", "Here");
                mr.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mr.start();
        });

        Button stop = findViewById(R.id.stopButton);
        stop.setOnClickListener( (v) -> {
            Log.v("Stop", "Here");
            Toast.makeText(this, "Stop recording", Toast.LENGTH_LONG);
            mr.stop();
            mr.release();
            mr = null;
        });

        Button play = findViewById(R.id.gpsButton);
        play.setOnClickListener( (v) -> {
            Intent intent = new Intent(AudioActivity.this, MediaplayerService.class);
            this.startService(intent);
        });
    }

    private void retrievePhotoFromGallery() {

    }

    private String getFilePath() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File dir = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File f = new File(dir, "record.mp3");
        return f.getPath();
    }
}