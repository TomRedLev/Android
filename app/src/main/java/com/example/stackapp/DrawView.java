package com.example.stackapp;

import static com.example.stackapp.DrawActivity.brush;
import static com.example.stackapp.DrawActivity.path;
import static com.example.stackapp.DrawActivity.clickTodoForLine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends View {
    public static List<Path> pathList = new ArrayList<>();
    public static List<Integer> colorList = new ArrayList<>();
    public static List<Paint.Style> styleList = new ArrayList<>();


    public ViewGroup.LayoutParams params;
    public static int brush_color = Color.BLACK;
    public static Paint.Style brushStyle = Paint.Style.STROKE;
    private float xLine;
    private float yLine;

    public DrawView(Context context) {
        super(context);
        init(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (clickTodoForLine > 0) {
            if (clickTodoForLine == 2) {
                this.xLine = event.getX();
                this.yLine = event.getY();
                path.moveTo(xLine, yLine);
                clickTodoForLine --;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                }
            }
            else {
                //path.moveTo(xLine, yLine);
                path.lineTo(event.getX(), event.getY());
                pathList.add(path);
                colorList.add(brush_color);
                styleList.add(brushStyle);
                clickTodoForLine = 0;
            }
        }
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                pathList.add(path);
                colorList.add(brush_color);
                styleList.add(brushStyle);
                break;
            default:
                return false;
        }
        postInvalidate();
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0 ; i < pathList.size() ; i++) {
            brush.setColor(colorList.get(i));
            brush.setStyle(styleList.get(i));
            canvas.drawPath(pathList.get(i), brush);
            invalidate();
        }
    }

    private void init(Context context) {
        brush.setAntiAlias(true);
        brush.setColor(Color.BLACK);
        brush.setStyle(brushStyle);
        brush.setStrokeCap(Paint.Cap.ROUND);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(8f);

        params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

}
