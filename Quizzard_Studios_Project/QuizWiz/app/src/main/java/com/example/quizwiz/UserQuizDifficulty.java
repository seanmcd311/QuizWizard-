package com.example.quizwiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserQuizDifficulty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_quiz_difficulty);

        final Button easy = findViewById(R.id.easyDifficulty);
        easy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                openQuiz(0);
            }
        });

        final Button medium = findViewById(R.id.mediumDifficulty);
        medium.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                openQuiz(1);
            }
        });

        final Button hard = findViewById(R.id.hardDifficulty);
        hard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                openQuiz(2);
            }
        });
    }

    //Start the quiz after passing the parameters
    private void openQuiz(int difficulty)
    {
        Intent intent = new Intent(this, UserQuizMain.class);
        intent.putExtra("quizIndex", getIntent().getExtras().getInt("quizIndex"));
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }
}
