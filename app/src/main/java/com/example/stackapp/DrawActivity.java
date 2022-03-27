package com.example.stackapp;

import static com.example.stackapp.DrawView.brushStyle;
import static com.example.stackapp.DrawView.brush_color;
import static com.example.stackapp.DrawView.colorList;
import static com.example.stackapp.DrawView.pathList;
import static com.example.stackapp.DrawView.styleList;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class DrawActivity extends AppCompatActivity {

    public static Path path = new Path();
    public static Paint brush = new Paint();
    public static int clickTodoForLine = 0;

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

        Button line = findViewById(R.id.line);
        line.setOnClickListener((v) -> clickTodoForLine = 2);
    }

    public void currentColor(int color) {
        brush_color = color;
        path = new Path();
    }

    public void currentStyle(Paint.Style style) {
        brushStyle = style;
        path = new Path();
    }

}