package com.example.stackapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class AudioActivity extends AppCompatActivity {

    private MediaRecorder mr;
    private String audioPath;

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
                        case 0: retrievePhotoFromGallery(); sendAudioPath(); break;
                        case 1: recordAudio(); break;
                    }});
        AlertDialog p = pictureDialog.create();
        p.setCanceledOnTouchOutside(false);
        p.show();
    }

    private void sendAudioPath() {
        Intent intent = new Intent();
        intent.putExtra("audioPath", audioPath);
        System.out.println("LOL: " + audioPath);
        setResult(30000, intent);
        finish();
    }

    private void recordAudio() {
        System.out.println("WHAT2");
        Button record = findViewById(R.id.recordButton);
        record.setOnClickListener( (v) -> {
            Toast.makeText(this, "Start recording", Toast.LENGTH_LONG);
            mr = new MediaRecorder();
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);
            mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            audioPath = getFilePath();
            mr.setOutputFile(audioPath);
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
            sendAudioPath();
        });
        System.out.println("WHAT3");

        /*
        Button play = findViewById(R.id.gpsButton);
        play.setOnClickListener( (v) -> {
            Intent intent = new Intent(AudioActivity.this, MediaplayerService.class);
            this.startService(intent);
        });
         */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ) {
            Intent intent = new Intent();
            if (requestCode == 2000) {
                intent.putExtra("audioPath", data.getData().toString());
            }
            setResult(30000, intent);
        }
        System.out.println("WHAT1");
        finish();
    }

    private void retrievePhotoFromGallery() {
        /*
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(intent, 2000);

         */
        Intent videoIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(videoIntent, "Select Audio"), 2000);

    }

    private String getFilePath() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File dir = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File f = new File(dir, Calendar.getInstance().getTimeInMillis() + ".mp3");
        return f.getPath();
    }
}