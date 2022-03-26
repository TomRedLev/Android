package com.example.stackapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class ImageActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        showPictureDialog();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0: chooseFromGallery(); break;
                        case 1: chooseCamera(); break;
                    }});
        pictureDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void chooseCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            String[] perm = {Manifest.permission.CAMERA,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(perm, 1001);
        }
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2001);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ) {
            Intent intent = new Intent();
            if (requestCode == 2000) {
                intent.putExtra("imageUri", data.getData().toString());
            } else if (requestCode == 2001) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                String imagePath = saveImage(thumbnail);
                Uri path = addToGallery(imagePath);
                System.out.println("image: " + path.toString());
                //System.out.println("Well done my dear friend: " + data.getData().toString());
                intent.putExtra("imageUri", path.toString());
            }
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public String saveImage(Bitmap myBitmap) {
        if (!Environment.isExternalStorageManager()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            startActivity(intent);
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().toString());
        if (!wallpaperDirectory.exists()) {
            System.out.println("OKOK");
            wallpaperDirectory.mkdirs();
        }
        System.out.println("dir: " + wallpaperDirectory.getAbsolutePath().toString());
        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private Uri addToGallery(String imagePath) {
        Intent galleryIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri picUri = Uri.fromFile(f);
        galleryIntent.setData(picUri);
        this.sendBroadcast(galleryIntent);
        return picUri;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void chooseFromGallery() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            String[] perm = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(perm, 1001);
        }
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2000);
    }
}