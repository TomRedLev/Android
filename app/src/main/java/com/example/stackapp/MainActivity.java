package com.example.stackapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // TODO Add saved notes on device to retrieve them when restarting the app

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
        gridView.setAdapter(new GridViewActivities(this, notesName));

        gridView.setOnItemClickListener((parent, v, position, id) -> startActivity(notes.get(position)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.myNote) {
            this.inputDialog(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void inputDialog(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.inputlayout, null);
        EditText input = promptView.findViewById(R.id.userInput);

        new AlertDialog.Builder(this)
                .setView(promptView)
                .setPositiveButton("OK", (dialog, id) -> {
                    notesName.add(input.getText().toString());
                    notes.add(new Intent(context, Note.class));
                    gridView.setAdapter(new GridViewActivities(context, notesName));
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}