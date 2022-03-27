package com.example.stackapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<String> notesName;
    private ArrayList<Intent> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesName = new ArrayList<>();
        notes = new ArrayList<>();
        gridView = findViewById(R.id.gridView);
        retrieveNotesName();
        gridView.setAdapter(new GridViewActivities(this, notesName));

        gridView.setOnItemClickListener((parent, v, position, id) -> {
            Intent i = notes.get(position);
            i.putExtra("noteName", notesName.get(position));
            startActivity(i);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("notesName", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("gridViewNames", TextUtils.join(",", notesName));
        editor.commit();
    }

    private void retrieveNotesName() {
        SharedPreferences preferences = getSharedPreferences("notesName", MODE_PRIVATE);
        String text = preferences.getString("gridViewNames", "");
        String[] splitted_text = text.split(",");
        for (String s : splitted_text) {
            if (!s.equals("")) {
                notesName.add(s);
                notes.add(new Intent(this, Note.class));
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addNote) {
            this.inputDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void inputDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.inputlayout, null);
        EditText input = promptView.findViewById(R.id.userInput);

        new AlertDialog.Builder(this)
                .setView(promptView)
                .setPositiveButton("OK", (dialog, id) -> {
                    notesName.add(input.getText().toString());
                    notes.add(new Intent(this, Note.class));
                    gridView.setAdapter(new GridViewActivities(this, notesName));
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}