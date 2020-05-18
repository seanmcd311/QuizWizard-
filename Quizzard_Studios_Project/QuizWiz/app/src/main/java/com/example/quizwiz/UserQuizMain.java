package com.example.quizwiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class UserQuizMain extends AppCompatActivity {

    UserQuiz quiz;

    int time = 0;
    CountDownTimer countDownTimer;
    TextView timer;

    TextView quizName, question, progress;
    Button answer1, answer2, answer3, answer4;

    int correct = 0;
    int questionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_quiz_main);

        quiz = loadQuiz(getIntent().getExtras().getInt("quizIndex"));

        timer = findViewById(R.id.timer);

        quizName = findViewById(R.id.userQuizName);
        question = findViewById(R.id.question);
        progress = findViewById(R.id.progress);
        answer1 = findViewById(R.id.answerChoice1);

        //This allows you to choose the correct answer amongst the inputted options
        answer1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                chooseAnswer(getCurrentQuestion().getAnswer(1));
            }
        });

        answer2 = findViewById(R.id.answerChoice2);
        answer2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                chooseAnswer(getCurrentQuestion().getAnswer(2));
            }
        });

        answer3 = findViewById(R.id.answerChoice3);
        answer3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                chooseAnswer(getCurrentQuestion().getAnswer(3));
            }
        });

        answer4 = findViewById(R.id.answerChoice4);
        answer3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                chooseAnswer(getCurrentQuestion().getAnswer(4));
            }
        });

        setupUI();
    }

    //Stops the countdown clock
    @Override
    public void onDestroy()
    {
        super.onDestroy();

        stopCountDownTimer();
    }

    //Loads the saved quiz at this index
    private UserQuiz loadQuiz(int index)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("quizzes", null);
        Type type = new TypeToken<ArrayList<UserQuiz>>() {}.getType();

        ArrayList<UserQuiz> savedQuizzes = gson.fromJson(json, type);

        if (savedQuizzes == null)
        {
            savedQuizzes = new ArrayList<UserQuiz>();
        }

        return savedQuizzes.get(index);
    }

    //Sets up the initial layout for custom quiz
    private void setupUI()
    {
        quizName.setText(quiz.getQuizName());
        setTime();
        populateQuestion(questionIndex);
    }

    //Sets up the UI for the question at this index
    private void populateQuestion(int index)
    {
        resetTimer(time);
        setProgress();

        UserQuestion quizQuestion = quiz.getQuestion(index);

        question.setText(quizQuestion.getQuestion());

        answer1.setText(quizQuestion.getAnswer(1));
        answer2.setText(quizQuestion.getAnswer(2));
        answer3.setText(quizQuestion.getAnswer(3));
        answer4.setText(quizQuestion.getAnswer(4));
    }

    //Sets the time initially based on the passed difficulty
    private void setTime()
    {
        int difficulty = getIntent().getExtras().getInt("difficulty");

        if (difficulty == 0)
        {
            time = 30;
        }
        else if (difficulty == 1)
        {
            time = 20;
        }
        else
        {
            time = 10;
        }
    }

    //Resets the Countdown timer and initializes a new one
    private void resetTimer(int time)
    {
        stopCountDownTimer();

        countDownTimer = new CountDownTimer(time * 1000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                timer.setText("Seconds Remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish()
            {
                incrementQuestionIndex();
            }
        }.start();
    }

    //Increments number correct if correct; then moves to next question regardless
    private void chooseAnswer(String chosenAnswer)
    {
        if (isCorrectAnswer(chosenAnswer))
        {
            correct++;
        }

        incrementQuestionIndex();
    }

    //Checks to see if the question selected is the correct answer
    private boolean isCorrectAnswer(String chosenAnswer)
    {
        return chosenAnswer.equals(quiz.getQuestion(questionIndex).getCorrectAnswer());
    }

    //Ends quiz if no more questions and displays next question otherwise
    private void incrementQuestionIndex()
    {
        questionIndex++;

        if (questionIndex > quiz.getNumberOfQuestions() - 1)
        {
            openResultsActivity();
        }
        else
        {
            populateQuestion(questionIndex);
        }
    }

    //Shows the results of the quiz
    private void openResultsActivity()
    {
        Intent intent = new Intent(this, UserQuizResults.class);
        intent.putExtra("correct", correct);
        intent.putExtra("total", quiz.getNumberOfQuestions());
        startActivity(intent);
        finish();
    }

    //Returns the current question
    private UserQuestion getCurrentQuestion()
    {
        return quiz.getQuestion(questionIndex);
    }

    //Stops the countdown timer
    private void stopCountDownTimer()
    {
        if (countDownTimer != null)
        {
            countDownTimer.cancel();
        }
    }

    //Shows progress
    private void setProgress()
    {
        progress.setText(questionIndex + 1 + "/" + quiz.getNumberOfQuestions());
    }
}
