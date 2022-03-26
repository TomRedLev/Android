package com.example.stackapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Note extends AppCompatActivity {

    // TODO Add UTF-8 compatibility
    // TODO Add possibility to take photo even if library is full of photos
    private EditText editText;
    ActivityResultLauncher<Intent> NoteResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        editText = findViewById(R.id.editText);

        NoteResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                String image = data.getStringExtra("imageUri");
                addImage(Uri.parse(image),500, 500);
            }});
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
            startActivity(new Intent(this, DrawActivity.class));
        } else if (id == R.id.addAudio) {
            startActivity(new Intent(this, AudioActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void addImage(Uri image, int sizeX, int sizeY) {
        Drawable d = retrieveImage(image);
        d.setBounds(0, 0, sizeX, sizeY);
        int cursor = editText.getSelectionStart();
        editText.getText().insert(cursor, ".");
        cursor = editText.getSelectionStart();
        SpannableStringBuilder builder = new SpannableStringBuilder(editText.getText());
        builder.setSpan(new ImageSpan(d), cursor - ".".length(), cursor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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