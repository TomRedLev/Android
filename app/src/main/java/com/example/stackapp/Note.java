package com.example.stackapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class Note extends AppCompatActivity {

    // TODO Add UTF-8 compatibility
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editText = findViewById(R.id.editText);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    /*
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

        Button geo = findViewById(R.id.geoButton);
        geo.setOnClickListener( (v) -> {
            startActivity(new Intent(this, GeolocActivity.class));
        });
        */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addImage) {
            //SpannableStringBuilder ssb = new SpannableStringBuilder(" ");

            int resource = getResources().getIdentifier("@drawable/omg", null, getPackageName());
            Drawable d = getDrawable(resource);
            d.setBounds(0, 0, 500, 500); // TODO Add size in relation to screen size
            ImageSpan img = new ImageSpan(d);
            Spannable s = (Spannable) editText.getText();
            //ssb.append(editText.getText().toString());
            int index = editText.getSelectionStart() >= 0 ? editText.getSelectionStart() : 0;
            s.setSpan(img, 0, s.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            //editText.invalidate();
            /*
            String oriContent = editText.getText().toString();
            int index = editText.getSelectionStart() >= 0 ? editText.getSelectionStart() : 0;
            SpannableStringBuilder sBuilder = new SpannableStringBuilder(oriContent);
            sBuilder.insert(index, ssb);
            editText.setText(sBuilder.toString());
            //editText.setSelection(index + insertStr.length());
            */

            editText.setText(s, TextView.BufferType.EDITABLE);
        } else if (id == R.id.addDrawing) {
            startActivity(new Intent(this, DrawActivity.class));
        }
        //case R.id.addText: setContentView(new Intent(this, WriteActivity.class)); break;

        return super.onOptionsItemSelected(item);
    }
}