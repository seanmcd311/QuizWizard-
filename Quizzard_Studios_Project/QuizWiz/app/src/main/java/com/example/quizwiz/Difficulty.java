package com.example.quizwiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Difficulty extends AppCompatActivity {
 static int time_value, solidtimevalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
    }

    public void gotoeasy(View view) { // selected on the click of easy
        Intent intent = new Intent(this, Quizmain.class);//starts quiz
           time_value = 30000;//sets the time to 30s per question on easy
        solidtimevalue = 30000;
        startActivity(intent);
        finish();
    }
    public void gotomedium(View view) {  // seledted on the click of medium
        Intent intent = new Intent(this, Quizmain.class);//starts quiz
        time_value = 20000; //sets the time to 20s per question on medium
        solidtimevalue = 20000;
        startActivity(intent);
        finish();
    }
    public void gotoahard(View view) { //selected on the click of hard
        Intent intent = new Intent(this, Quizmain.class); //starts quiz
        time_value = 10000; //sets the time to 10s per question on hard
        solidtimevalue = 10000;
        startActivity(intent);
        finish();
    }

}
