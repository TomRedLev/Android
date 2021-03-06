package com.example.stackapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Note extends AppCompatActivity {

    // TODO Add UTF-8 compatibility
    private EditText editText;
    ActivityResultLauncher<Intent> NoteResultLauncher;
    private String noteName;

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences(noteName, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("note", editText.getText().toString());
        editor.commit();
    }

    private void retrieveEditText() {
        SharedPreferences preferences = getSharedPreferences(noteName, MODE_PRIVATE);
        String text = preferences.getString("note", "");
        String[] splitted_text = text.split("@@@");
        for (String s : splitted_text) {
            boolean match = s.matches("(content:/{2,3}(([a-z]+|[0-9]+)/)+[0-9]+)|(file:/{2,3}(([a-z]+|[0-9]+)/)+[0-9]+\\.[a-z]{3})");
            if (match) {
                addImage(Uri.parse(s), 500, 500);
            } else {
                int cursor = editText.getSelectionStart();
                editText.getText().insert(cursor, s);
                cursor = editText.getSelectionStart();
                editText.setSelection(cursor);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        editText = findViewById(R.id.editText);
        noteName = getIntent().getStringExtra("noteName");
        retrieveEditText();


        editText.setLinksClickable(true);
        editText.setAutoLinkMask(Linkify.ALL);
        editText.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(editText, Linkify.ALL);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Linkify.addLinks(s, Linkify.ALL);
            }
        });

        NoteResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == 10000) {
                Intent data = result.getData();
                String image = data.getStringExtra("imageUri");
                addImage(Uri.parse(image),500, 500);
            } else if (result.getResultCode() == 20000) {
                Intent data = result.getData();
                String link = data.getStringExtra("gpsLink");
                addPositionLink(link);
            } else if (result.getResultCode() == 30000) {
                Intent data = result.getData();
                String audioPath = data.getStringExtra("audioPath");
                addAudioLink(audioPath);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addImage) {
            Intent imageIntent = new Intent(this, ImageActivity.class);
            NoteResultLauncher.launch(imageIntent);
        } else if (id == R.id.addDrawing) {
            Intent drawIntent = new Intent(this, DrawActivity.class);
            NoteResultLauncher.launch(drawIntent);
        } else if (id == R.id.addAudio) {
            Intent audioIntent = new Intent(this, AudioActivity.class);
            NoteResultLauncher.launch(audioIntent);
        } else if (id == R.id.addGPS) {
            Intent gpsIntent = new Intent(this, GeolocActivity.class);
            NoteResultLauncher.launch(gpsIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void addAudioLink(String path) {
        // TODO
        /*
        SpannableString ss = new SpannableString(
                "text4: Click here to dial the phone.");

        ss.setSpan(new URLSpan("tel:4155551212"), 13, 17,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView t4 = (TextView) findViewById(R.id.text4);
        t4.setText(ss);

         */
    }

    private void addPositionLink(String link) {
        editText.getText().insert(editText.getSelectionStart(), link + " ");
        editText.setSelection(editText.getSelectionStart());
    }

    private void addImage(Uri image, int sizeX, int sizeY) {
        Drawable d = retrieveImage(image);
        d.setBounds(0, 0, sizeX, sizeY);
        int cursor = editText.getSelectionStart();
        String name = "@@@" + image.toString() + "@@@";
        editText.getText().insert(cursor, name);
        cursor = editText.getSelectionStart();
        SpannableStringBuilder builder = new SpannableStringBuilder(editText.getText());
        builder.setSpan(new ImageSpan(d), cursor - name.length(), cursor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setText(builder);
        editText.setSelection(cursor);
    }

    private Drawable retrieveImage(Uri image) {
        Drawable d;
        try {
            InputStream inputStream = null;
            inputStream = getContentResolver().openInputStream(image);
            d = Drawable.createFromStream(inputStream, image.toString() );
        } catch (FileNotFoundException e) {
            int resource = getResources().getIdentifier("@drawable/omg", null, getPackageName());
            d = getDrawable(resource);
        }
        return d;
    }

}