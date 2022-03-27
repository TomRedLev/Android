package com.example.stackapp;

import static com.example.stackapp.DrawView.brushStyle;
import static com.example.stackapp.DrawView.brush_color;
import static com.example.stackapp.DrawView.colorList;
import static com.example.stackapp.DrawView.pathList;
import static com.example.stackapp.DrawView.styleList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class DrawActivity extends AppCompatActivity {

    public static Path path = new Path();
    public static Paint brush = new Paint();

    public Bitmap getBitmap() {
        getWindow().getDecorView().setDrawingCacheEnabled(true);
        getWindow().getDecorView().buildLayer();
        Bitmap bmp = Bitmap.createBitmap(getWindow().getDecorView().getDrawingCache());
        getWindow().getDecorView().setDrawingCacheEnabled(false);
        return bmp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        Button stroke = findViewById(R.id.stroke);
        stroke.setOnClickListener((v) -> currentStyle(Paint.Style.STROKE));

        Button fillAndStroke = findViewById(R.id.fillAndStroke);
        fillAndStroke.setOnClickListener((v) -> currentStyle(Paint.Style.FILL_AND_STROKE));

        Button eraser = findViewById(R.id.eraser);
        eraser.setOnClickListener((v) -> {
            pathList.clear();
            colorList.clear();
            styleList.clear();
            path.reset();
        });

        Button blackColor = findViewById(R.id.blackColor);
        blackColor.setOnClickListener((v) -> currentColor(Color.BLACK));

        Button redColor = findViewById(R.id.redColor);
        redColor.setOnClickListener((v) -> currentColor(Color.RED));
    }

    public void currentColor(int color) {
        brush_color = color;
        path = new Path();
    }

    public void currentStyle(Paint.Style style) {
        brushStyle = style;
        path = new Path();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.saveNote) {
            Intent intent = new Intent();
            Bitmap bmp = getBitmap();
            String imagePath = saveImage(bmp);
            Uri path = addToGallery(imagePath);
            intent.putExtra("imageUri", path.toString());
            setResult(10000, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private Uri addToGallery(String imagePath) {
        Intent galleryIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri picUri = Uri.fromFile(f);
        galleryIntent.setData(picUri);
        this.sendBroadcast(galleryIntent);
        return picUri;
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().toString());
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
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

}