package com.example.quizwiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserQuizResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_quiz_results);

        final TextView correct = findViewById(R.id.correctResult);
        correct.setText("You got " + getIntent().getExtras().getInt("correct") + " out of " + getIntent().getExtras().getInt("total") + " correct.");

        final Button returnButton = findViewById(R.id.returnToMain);
        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                returnToHome();
            }
        });
    }

    //Goes back to the main menu
    private void returnToHome()
    {
        startActivity(new Intent(this, MainActivity.class));
    }
}
