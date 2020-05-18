package com.example.quizwiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Stats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //these shows the score values form the MainActivity.java in the stats screen
        TextView shs = findViewById(R.id.SHS);
        shs.setText(String.valueOf(MainActivity.sport_score));

        TextView hhs = findViewById(R.id.HHS);
        hhs.setText(String.valueOf(MainActivity.his_score));

        TextView ths = findViewById(R.id.THS);
        ths.setText(String.valueOf(MainActivity.tv_score));

        TextView ohs = findViewById(R.id.OHS);
        ohs.setText(String.valueOf(MainActivity.high_score));
    }
}
