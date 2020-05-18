package com.example.quizwiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;



public class MainActivity extends AppCompatActivity {
    public static Vector<String> sentences = new Vector<>(); // vector that stores the strings (questions and answers) extracted from the .txt files
     static  int high_score = 0; // starts overall high score
    static  int sport_score = 0;// starts sports high score
    static  int his_score = 0;// starts history high score
    static  int tv_score = 0; // starts tv/film high score


    // these are logic values that decide which high score goes to which catagory
    static  int sport_logic = 0;
    static  int his_logic = 0;
    static  int tv_logic = 0;
    String line;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView hsc = findViewById(R.id.HS);
        hsc.setText(highscore());
        //Example of a call to a native method
        //TextView tv = findViewById(R.id.action_text);
        //tv.setText(stringFromJNI());

        final Button customQuizzes = findViewById(R.id.UserCreatedQuizzes);
        customQuizzes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                openEditUserQuizzes();
            }
        });
    }

    public void openEditUserQuizzes()
    {
        startActivity(new Intent(this, EditUserQuizzes.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void gotosports(View view) throws IOException { // this is triggered by clicking the sports catagory
        Intent intent = new Intent(this, Difficulty.class);//goes to difficulty select
        startActivity(intent);
        //sports logic enabled
        sport_logic = 1;
        his_logic = 0;
        tv_logic = 0;

        //clears sentences vector for each time a new category is selected
        sentences.clear();


        //this opens the sports_questions.txt file
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("sports_questions.txt"), "UTF-8")); //retrieves file from assets folder
            while ((line = reader.readLine()) != null) {
                sentences.add(line); //this adds each string to the sentences vector
            } }
                catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }


    }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void gotohistory(View view) throws IOException {// this is triggered by clicking the history catagory
        Intent intent = new Intent(this, Difficulty.class);//goes to difficulty select
        startActivity(intent);

            //history logic enabled
            sport_logic = 0;
            his_logic = 1;
            tv_logic = 0;

            //clears sentences vector for each time a new category is selected
            sentences.clear();

            //this opens the history_questions.txt file
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(
                        new InputStreamReader(getAssets().open("history_questions.txt"), "UTF-8"));//retrieves file from assets folder
                while ((line = reader.readLine()) != null) {
                    sentences.add(line); //this adds each string to the sentences vector
                } }
            catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void gotoTV(View view) throws IOException { // this is triggered by clicking the TV/Film catagory
        Intent intent = new Intent(this, Difficulty.class);//goes to difficulty select
        startActivity(intent);

        //TV logic enabled
        sport_logic= 0;
        his_logic = 0;
        tv_logic = 1;
        BufferedReader reader = null;

        //clears sentences vector for each time a new category is selected
        sentences.clear();

        //this opens the tv_questions.txt file
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("tv_questions.txt"), "UTF-8"));//retrieves file from assets folder
            while ((line = reader.readLine()) != null) {
                sentences.add(line); //this adds each string to the sentences vector
            } }
        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }

    }
    public  String highscore(){ //this method sets each score value

        if(high_score < Quizmain.new_score){
            high_score = Quizmain.new_score;
        }
            if(sport_logic == 1 && sport_score < Quizmain.new_score){
                    sport_score = Quizmain.new_score;
            }
        if(his_logic == 1 && his_score < Quizmain.new_score){
            his_score = Quizmain.new_score;
        }
        if(tv_logic== 1 && tv_score < Quizmain.new_score){
            tv_score = Quizmain.new_score;
        }

        String hs = String.valueOf(high_score);
        return hs;
    }

    public void gostats(View view) { //this goes to the stats screen
        Intent intent = new Intent(this, Stats.class);

        startActivity(intent);
    }
}
