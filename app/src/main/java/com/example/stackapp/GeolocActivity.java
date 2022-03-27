package com.example.stackapp;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class GeolocActivity extends AppCompatActivity {

    // TODO After first time when asked for permission, location is null
    // TODO Need to find a way to retrieve it without having to recall GPS

    private float longitude;
    private float latitude;

    private static final String[] LOCATION_PERMS = {
            ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION
    };

    private void checkPermission() {
       ActivityCompat.requestPermissions(this,
                    LOCATION_PERMS,
                    2003);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geoloc);
        checkPermission();
        Button getLocation = findViewById(R.id.gpsButton);
        getLocation.setOnClickListener((v) -> {
            String link = getPosition();
            Intent intent = new Intent();
            intent.putExtra("gpsLink", link);
            setResult(20000, intent);
            finish();
        });

    }

    protected String getPosition() {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                | ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }

        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                return;
            }
        });
        if (lm != null) {
            Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                latitude = (float) loc.getLatitude();
                longitude = (float) loc.getLongitude();
            }
        }
        this.latitude = latitude;
        this.longitude = longitude;

        return getLocationLink();
    }

    private String getLocationLink() {
        return "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;
    }

}
