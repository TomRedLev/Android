package com.example.stackapp;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class Geoloc {

    private float longitude;
    private float latitude;

    private static final String[] LOCATION_PERMS={
            ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION
    };
    /*
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button draw = findViewById(R.id.drawButton);
        draw.setOnClickListener( (v) -> {
            startActivity(new Intent(this, DrawActivity.class));
        });

        Button write = findViewById(R.id.writeButton);
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

        if (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(LOCATION_PERMS, 1337);
        }

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                return ;
            }
        });
        if (lm != null) {
            Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                latitude = (float) loc.getLatitude();
                longitude = (float) loc.getLongitude();
            }
        }

        Button geo = findViewById(R.id.geoButton);
        geo.setOnClickListener( (v) -> {
            Log.i("Link", "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude);
        });
    }

     */
}
