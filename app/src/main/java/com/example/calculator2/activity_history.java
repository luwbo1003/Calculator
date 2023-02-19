package com.example.calculator2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class activity_history extends AppCompatActivity {
  TextView hist;
    ArrayList<String> result = new ArrayList<String>();
    ArrayList<String> solution = new ArrayList<String>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        hist = findViewById(R.id.tvhis);
        Bundle extras = getIntent().getExtras();
        if (extras != null ){

            result = extras.getStringArrayList("result");
            solution = extras.getStringArrayList("solution");
        }
        String history = "";
        for (int i = result.size()-1 ; i >= 0 ; i--) {
            history = history + solution.get(i) + " = " + result.get(i) + "\n\n";

        }
        hist.setText(history);
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (hist.getText().toString()!=null)
            outState.putString("result", hist.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.get("result")!=null)
            hist.setText(savedInstanceState.get("result").toString());

    }
}