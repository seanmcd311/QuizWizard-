package com.example.quizwiz;

import androidx.appcompat.app.AppCompatActivity;


import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class Quizmain extends AppCompatActivity {

    Random rand = new Random();
    int rand_num,rand_num1,rand_num2, rand_num3, find_colon;
    int found,found1,found2,found3, found_quote,found_quote1,found_quote2,found_quote3, find_end_of_ques;
    String ans,ans1,ans2,ans3, final_ans, sentence,sentence1, sentence2,sentence3, quest, player_choice;
    String incorrect1,incorrect2,incorrect3;
    int lives, passes;
    int score = 0;
    static int new_score;

    //this integer array is used to randomize the button ids so the answers can be displayed randomly
    Integer[] a =  { R.id.button7, R.id.button8, R.id.button11, R.id.button12};

    Button tv1, tv2,tv3,tv4;

    //this gets the time set from the difficulty selection
    int newtime = Difficulty.time_value;

    Dialog confirmationpass;
    TextView Passask;
    Button passbutton, closePop;

    @Override
    public void onBackPressed() {
// super.onBackPressed();
// Not calling **super**, disables back button in current screen.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizmain);

        confirmationpass = new Dialog(this);

        //sets lives and passes
        lives = 3;
        passes = 3;


        //this is the timer function
       final CountDownTimer countDownTimer = new CountDownTimer( newtime, 1000) {
            TextView timer = findViewById(R.id.textView7);

            public void onTick(long millisUntilFinished) {

                timer.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                //gets new question and resets timer if you still have lives
                timeout();
                cancel();
                if (lives > 0 ) {
                    start();
                }
            }

        }.start(); //timer starts

        //displays question, score, lives and passes
        TextView tv = findViewById(R.id.mainquiestion);
        tv.setText(getquestion());

        String[] b =  { getans(), getincorrect1(), getincorrect2(), getincorrect3()};// array for randomized answers
        TextView nv = findViewById(R.id.displife);
        nv.setText(life());

        TextView nv1 = findViewById(R.id.dispscore);
        nv1.setText(score());

        TextView nv2 = findViewById(R.id.disppass);
        nv2.setText(getpass());

        //randomizes button ids
        Collections.shuffle(Arrays.asList(a));
        // randomizes answers
        Collections.shuffle(Arrays.asList(b));

        //sets answers and ids
        tv1 = findViewById(a[0]);
        tv1.setText(b[0]);

        tv2 = findViewById(a[1]);
        tv2.setText(b[1]);

        tv3 = findViewById(a[2]);
        tv3.setText(b[2]);

        tv4 = findViewById(a[3]);
        tv4.setText(b[3]);



        /* these are on click listeners that retrieve new questions and answers and
        randomize them again so the correct answer doesn't stay in the same place,
        as well as checks answers and resets timers
         */
        tv1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*this if statement increments the score if the selected answer equals the correct answer
                 and decrease your lives if it doesn't
                 */
                if(tv1.getText() == getans()) {
                    scoreplus();
                }else{

                    lifeminus();
                }

                //this stops the timer and resets it if you still have lives
                countDownTimer.cancel();

                if (lives > 0 ) {
                    countDownTimer.start();
                }

                TextView nv = findViewById(R.id.displife);
                nv.setText(life());

                TextView nv1 = findViewById(R.id.dispscore);
                nv1.setText(score());

                TextView tv = findViewById(R.id.mainquiestion);
                tv.setText(getquestion());
                String[] b =  { getans(), getincorrect1(), getincorrect2(), getincorrect3()};


                Collections.shuffle(Arrays.asList(a));
                Collections.shuffle(Arrays.asList(b));

                tv1.setText(b[0]);
                tv2.setText(b[1]);
                tv3.setText(b[2]);
                tv4.setText(b[3]);
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(tv2.getText() == getans()) {
                    scoreplus();
                }else{

                    lifeminus();
                }
                countDownTimer.cancel();

                if (lives > 0 ) {
                    countDownTimer.start();
                }

                TextView nv = findViewById(R.id.displife);
                nv.setText(life());

                TextView nv1 = findViewById(R.id.dispscore);
                nv1.setText(score());

                TextView tv = findViewById(R.id.mainquiestion);
                tv.setText(getquestion());
                String[] b =  { getans(), getincorrect1(), getincorrect2(), getincorrect3()};


                Collections.shuffle(Arrays.asList(a));
                Collections.shuffle(Arrays.asList(b));

                tv1.setText(b[0]);
                tv2.setText(b[1]);
                tv3.setText(b[2]);
                tv4.setText(b[3]);
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (tv3.getText() == getans()) {
                    scoreplus();
                } else {

                    lifeminus();
                }
                countDownTimer.cancel();

                if (lives > 0) {
                    countDownTimer.start();
                }

                TextView nv = findViewById(R.id.displife);
                nv.setText(life());

                TextView nv1 = findViewById(R.id.dispscore);
                nv1.setText(score());

                TextView tv = findViewById(R.id.mainquiestion);
                tv.setText(getquestion());
                String[] b = {getans(), getincorrect1(), getincorrect2(), getincorrect3()};


                Collections.shuffle(Arrays.asList(a));
                Collections.shuffle(Arrays.asList(b));

                tv1.setText(b[0]);
                tv2.setText(b[1]);
                tv3.setText(b[2]);
                tv4.setText(b[3]);
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(tv4.getText() == getans()) {
                    scoreplus();
                }else{

                    lifeminus();
                }
                countDownTimer.cancel();

                if (lives > 0 ) {
                    countDownTimer.start();
                }

                TextView nv = findViewById(R.id.displife);
                nv.setText(life());

                TextView nv1 = findViewById(R.id.dispscore);
                nv1.setText(score());

                TextView tv = findViewById(R.id.mainquiestion);
                tv.setText(getquestion());
                String[] b =  { getans(), getincorrect1(), getincorrect2(), getincorrect3()};


                Collections.shuffle(Arrays.asList(a));
                Collections.shuffle(Arrays.asList(b));

                tv1.setText(b[0]);
                tv2.setText(b[1]);
                tv3.setText(b[2]);
                tv4.setText(b[3]);
            }
        });



        //Sensor for shake
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();
        //Waits for a shake
        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
            //If there is a shake, a pop-up window will come up
            public void onShake(){
                confirmationpass.setContentView(R.layout.dialoguebox);
                closePop = (Button) confirmationpass.findViewById(R.id.closePop);
                passbutton = (Button) confirmationpass.findViewById(R.id.passbutton);
                Passask = (TextView) confirmationpass.findViewById(R.id.Passask);

                //Button to close popup with no pass
                closePop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmationpass.dismiss();
                    }
                });
                //Button to close popup with pass
                passbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countDownTimer.cancel();
                        if (lives > 0 ) {
                            countDownTimer.start();
                        }
                        passminus();
                        confirmationpass.dismiss();
                    }
                });
                //Showing pop up
                confirmationpass.getWindow();
                confirmationpass.show();
            }
        });
    }


    //Runs the Sensor to wait for a shake
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;

    //Keeps the sensor going or starts it again after pause
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }
    //Keeps the sensor on pause
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }




   //this is called upon when the time runs out. it works similarly to the onclick listeners
   public void timeout(){

       lifeminus();

       TextView nv = findViewById(R.id.displife);
       nv.setText(life());

       TextView nv1 = findViewById(R.id.dispscore);
       nv1.setText(score());


       TextView tv = findViewById(R.id.mainquiestion);
       tv.setText(getquestion());
       String[] b =  { getans(), getincorrect1(), getincorrect2(), getincorrect3()};


       Collections.shuffle(Arrays.asList(a));
       Collections.shuffle(Arrays.asList(b));

       tv1.setText(b[0]);
       tv2.setText(b[1]);
       tv3.setText(b[2]);
       tv4.setText(b[3]);

   }




/*this is the method that returns the question string and retrives the correct answer from the same
   string in the sentances vector*/
    public String getquestion() {

        rand_num = rand.nextInt(MainActivity.sentences.size());

        sentence = MainActivity.sentences.get(rand_num);
        found = sentence.indexOf("\",");//Find instance of ','
        ans = sentence.substring(found+4, sentence.length());// Taking subtring from ','+3 to the end

        found_quote = ans.indexOf('\"'); //Finding next "
        final_ans = ans.substring(0,found_quote); //Taking a new substring from initial ans to the found_quote

        find_colon = sentence.indexOf(':');
        String quest_part = sentence.substring(find_colon+1,sentence.length());
        find_end_of_ques = quest_part.indexOf("\",");
        quest = quest_part.substring(0, find_end_of_ques);

        return quest;
    }

    //returns string value of final answer
    public String getans() {
        return final_ans;
    }

        /*these retrieve the incorrect answers that are put in random buttons
            they work nearly the same way as the get question function does*/
    public  String getincorrect1(){

        rand_num1 = rand.nextInt(MainActivity.sentences.size());
        while(rand_num1 == rand_num){
        rand_num1 = rand.nextInt(MainActivity.sentences.size());}

        sentence1 = MainActivity.sentences.get(rand_num1);
        found1 = sentence1.indexOf("\","); //Find instance of ','
        ans1 = sentence1.substring(found1 + 4, sentence1.length()); // Taking subtring from ','+3 to the end

        found_quote1 = ans1.indexOf('\"'); //Finding next "
        incorrect1 = ans1.substring(0, found_quote1); //Taking a new substring from initial ans to the found_quote

            return incorrect1;

    }
    public String getincorrect2(){

        rand_num2 = rand.nextInt(MainActivity.sentences.size());
        while(rand_num2 == rand_num1 || rand_num2 == rand_num){
            rand_num2 = rand.nextInt(MainActivity.sentences.size());}

        sentence2 = MainActivity.sentences.get(rand_num2);
        found2 = sentence2.indexOf("\","); //Find instance of ','
        ans2 = sentence2.substring(found2 + 4, sentence2.length()); // Taking subtring from ','+3 to the end

        found_quote2 = ans2.indexOf('\"'); //Finding next "
        incorrect2 = ans2.substring(0, found_quote2); //Taking a new substring from initial ans to the found_quote

        return incorrect2;

    }
    public String getincorrect3(){

            rand_num3 = rand.nextInt(MainActivity.sentences.size());
            while(rand_num3 == rand_num2 || rand_num3 == rand_num1 || rand_num3 == rand_num)
                {
                    rand_num3 = rand.nextInt(MainActivity.sentences.size());
                }

                sentence3 = MainActivity.sentences.get(rand_num3);
                found3 = sentence3.indexOf("\","); //Find instance of ','
                ans3 = sentence3.substring(found3 + 4, sentence3.length()); // Taking subtring from ','+3 to the end

                found_quote3 = ans3.indexOf('\"'); //Finding next "
                incorrect3 = ans3.substring(0, found_quote3); //Taking a new substring from initial ans to the found_quote

                return incorrect3;

            }



//these increment and decrement the score and life values respectively
    public  void scoreplus(){
        ++score;
        new_score = score;

    }
    public void lifeminus(){
        --lives;
        if (lives < 1 ) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }



    //these return the life and score values as strings
    public  String life(){

       String life = String.valueOf(lives);
        return life;
    }
    public String score(){

        return String.valueOf(score);
       
    }


    // this is the method that will decrement the passes and move to the next question
    public void passminus(){
        --passes;

       TextView nv = findViewById(R.id.displife);
        nv.setText(life());

        TextView nv1 = findViewById(R.id.dispscore);
        nv1.setText(score());

        TextView nv2 = findViewById(R.id.disppass);
        nv2.setText(getpass());

        TextView tv = findViewById(R.id.mainquiestion);
        tv.setText(getquestion());
        String[] b =  { getans(), getincorrect1(), getincorrect2(), getincorrect3()};


        Collections.shuffle(Arrays.asList(a));
        Collections.shuffle(Arrays.asList(b));

        tv1.setText(b[0]);
        tv2.setText(b[1]);
        tv3.setText(b[2]);
        tv4.setText(b[3]);
        //This stops the shake sensor when all passes have been used
        if (passes < 1 ) {

            onPause();
        }

    }
    public String getpass(){ // this is sets the passes value to a string

        return ("Passes:  " + passes);
    }
}




