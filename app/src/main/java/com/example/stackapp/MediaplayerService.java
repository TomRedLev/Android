package com.example.stackapp;

import android.app.Service;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;


public class MediaplayerService extends Service {
    private MediaPlayer mp;

    @Override
    public void onCreate() {
        mp = new MediaPlayer();
        try {
            mp.setDataSource(getFilePath());
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }

    private String getFilePath() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File dir = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File f = new File(dir, "record.mp3");
        return f.getPath();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
