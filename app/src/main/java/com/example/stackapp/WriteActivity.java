package com.example.stackapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Button but = (Button) findViewById(R.id.button);
        EditText edit = (EditText) findViewById(R.id.textInputEditText2);
        TextView text = (TextView) findViewById(R.id.textView2);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText(edit.getText().toString());
            }
        });
    }
}