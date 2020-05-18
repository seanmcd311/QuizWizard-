package com.example.quizwiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateQuiz extends AppCompatActivity {

    //EditText that the user enters the desired quiz name
    EditText quizInput;

    //TextView that displays the question number being created/edited
    TextView questionNumber;

    //EditText that the user enters the desired question
    EditText questionInput;

    //Each answer input field
    EditText answer1Input;
    EditText answer2Input;
    EditText answer3Input;
    EditText answer4Input;

    //Stores all the inputs for iteration later
    ArrayList<EditText> answerInputs;

    //each answer choice checkbox
    CheckBox answer1Choice;
    CheckBox answer2Choice;
    CheckBox answer3Choice;
    CheckBox answer4Choice;

    //Stores all the checkboxes for iteration later
    ArrayList<CheckBox> answerChoices;

    //Button to go to the previous question in the question queue
    Button previousQuestion;

    //Button to go to the next question in the question queue
    Button nextQuestion;

    //Button to add the question to the quiz from the fields
    Button addQuestion;

    //Button to delete the current question
    Button deleteQuestion;

    //Button to return to the EditUserQuizzes activity
    Button cancelQuizCreation;

    //Button to create and save the quiz from the fields
    Button finishQuizCreation;

    //TextView that displays relevant warnings
    TextView warning;

    //Stores the user-created quiz information which will be saved
    UserQuiz userQuiz = new UserQuiz();

    //Keeps track of whether the current question is one that has already been created
    //Determines whether question can be deleted
    boolean bPreviouslyCreatedQuestion = false;

    //Keeps track of the order of the questions
    //Determines which question control buttons are enabled
    int questionIndex = 0;

    //Hides the keyboard
    private void hideSoftKeyboard()
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    //Recursive function to tell if an input field loses focus so that we can hide the keyboard
    private void setupUI(View view)
    {
        if (!(view instanceof EditText))
        {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup)
        {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);
        setupUI(findViewById(R.id.parent));

        quizInput = findViewById(R.id.quizName);
        questionNumber = findViewById(R.id.questionNumber);
        questionInput = findViewById(R.id.questionInput);

        answer1Input = findViewById(R.id.answer1Input);
        answer2Input = findViewById(R.id.answer2Input);
        answer3Input = findViewById(R.id.answer3Input);
        answer4Input = findViewById(R.id.answer4Input);

        answerInputs = new ArrayList<EditText>(Arrays.asList(answer1Input, answer2Input, answer3Input, answer4Input));

        answer1Choice = findViewById(R.id.isAnswer1);
        answer2Choice = findViewById(R.id.isAnswer2);
        answer3Choice = findViewById(R.id.isAnswer3);
        answer4Choice = findViewById(R.id.isAnswer4);

        answerChoices = new ArrayList<CheckBox>(Arrays.asList(answer1Choice, answer2Choice, answer3Choice, answer4Choice));

        previousQuestion = findViewById(R.id.previousQuestion);
        nextQuestion = findViewById(R.id.nextQuestion);
        addQuestion = findViewById(R.id.addQuestionButton);
        deleteQuestion = findViewById(R.id.deleteQuestion);

        cancelQuizCreation = findViewById(R.id.cancelQuizCreation);
        finishQuizCreation = findViewById(R.id.finishQuiz);

        warning = findViewById(R.id.warning);

        previousQuestion.setEnabled(false);
        nextQuestion.setEnabled(false);
        deleteQuestion.setEnabled(false);

        answer1Choice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectCorrectAnswerChoice(answer1Choice);
            }
        });

        answer2Choice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectCorrectAnswerChoice(answer2Choice);
            }
        });

        answer3Choice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectCorrectAnswerChoice(answer3Choice);
            }
        });

        answer4Choice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectCorrectAnswerChoice(answer4Choice);
            }
        });

        addQuestion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if (addQuestion())
                {
                    questionIndex = userQuiz.getNumberOfQuestions();
                    bPreviouslyCreatedQuestion = false;
                    refreshQuestionDisplay();
                }
            }
        });

        previousQuestion.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                questionIndex--;
                bPreviouslyCreatedQuestion = true;
                refreshQuestionDisplay();
            }
        });

        nextQuestion.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                questionIndex++;
                bPreviouslyCreatedQuestion = questionIndex != userQuiz.getNumberOfQuestions();
                refreshQuestionDisplay();
            }
        });

        deleteQuestion.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                deleteQuestion();
                questionIndex = userQuiz.getNumberOfQuestions();
                bPreviouslyCreatedQuestion = false;
                refreshQuestionDisplay();
            }
        });

        finishQuizCreation.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if (isQuizValid())
                {
                    if (updatingQuiz())
                    {
                        updateQuiz();
                    }
                    else
                    {
                        saveQuiz();
                    }

                    returnToEditUserQuizzes();
                }
                else
                {
                    warning.setText("Quiz must have a name and at least 1 question.");
                }
            }
        });

        cancelQuizCreation.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                returnToEditUserQuizzes();
            }
        });

        //This focuses the keyboard on the quiz input field initially
        if (quizInput.requestFocus())
        {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        if (updatingQuiz())
        {
            setupUpdateQuiz();
        }
    }

    //Toggles all other checkboxes off when the correct answer is selected
    private void selectCorrectAnswerChoice(CheckBox correctChoice)
    {
        for (CheckBox answerChoice : answerChoices)
        {
            if (correctChoice != answerChoice/* && answerChoice.isChecked()*/)
            {
                //answerChoice.toggle();
                answerChoice.setChecked(false);
            }
        }
    }

    //Checks to see if an answer is filled in
    private boolean isInputEmpty(EditText input)
    {
        return input.getText().toString().trim().equals("");
    }

    //Checks to see if a correct answer is selected
    private boolean isCorrectAnswerSelected()
    {
        for (CheckBox answerChoice : answerChoices)
        {
            if (answerChoice.isChecked())
            {
                return true;
            }
        }

        return false;
    }

    //Checks to see if any answers are the same
    private boolean areAnyChoicesSame()
    {
        for (int i = 0; i < answerInputs.size(); i++)
        {
            for (int j = 0; j < answerInputs.size(); j++)
            {
                if (i != j && answerInputs.get(i).getText().toString().equals(answerInputs.get(j).getText().toString()))
                {
                    return true;
                }
            }
        }

        return false;
    }

    //Checks to see if all the relevant answer fields are filled in
    private boolean isQuestionValid()
    {
        return !isInputEmpty(questionInput) && !isInputEmpty(answer1Input) && !isInputEmpty(answer2Input) && !isInputEmpty(answer3Input) && !isInputEmpty(answer4Input) && isCorrectAnswerSelected() && !areAnyChoicesSame();
    }

    //Returns the question from the fields assuming they are filled in properly
    private UserQuestion getQuestion()
    {
        return new UserQuestion(questionInput.getText().toString(), answer1Input.getText().toString(), answer2Input.getText().toString(), answer3Input.getText().toString(), answer4Input.getText().toString(), getCorrectAnswerAsString());
    }

    //Gets the correct answer choice as a string assuming one is selected as the correct choice
    private String getCorrectAnswerAsString()
    {
        if (answer1Choice.isChecked())
        {
            return answer1Input.getText().toString();
        }
        else if (answer2Choice.isChecked())
        {
            return answer2Input.getText().toString();
        }
        else if (answer3Choice.isChecked())
        {
            return answer3Input.getText().toString();
        }
        else
        {
            return answer4Input.getText().toString();
        }
    }

    //Reverts question fields to default state
    private void resetQuestionFields()
    {
        questionNumber.setText(questionIndex + 1 + ".");

        questionInput.setText("");

        for (CheckBox answerChoice : answerChoices)
        {
            answerChoice.setChecked(false);
        }

        for (EditText answerInput :answerInputs)
        {
            answerInput.setText("");
        }
    }

    //Populates UI elements from a previously created question
    private void fillFieldsFromQuestion(UserQuestion question)
    {
        questionInput.setText(question.getQuestion());
        answer1Input.setText(question.getAnswer(1));
        answer2Input.setText(question.getAnswer(2));
        answer3Input.setText(question.getAnswer(3));
        answer4Input.setText(question.getAnswer(4));

        if (question.getAnswer(1).equals(question.getCorrectAnswer()))
        {
            answer1Choice.setChecked(true);
        }
        else if (question.getAnswer(2).equals(question.getCorrectAnswer()))
        {
            answer2Choice.setChecked(true);
        }
        else if (question.getAnswer(3).equals(question.getCorrectAnswer()))
        {
            answer3Choice.setChecked(true);
        }
        else
        {
            answer4Choice.setChecked(true);
        }
    }

    //Checks if the question is valid and adds the question to the UserQuiz if so, otherwise outputs a warning
    private boolean addQuestion()
    {
        if (isQuestionValid())
        {
            if (bPreviouslyCreatedQuestion)
            {
                userQuiz.updateQuestion(questionIndex, getQuestion());
            }
            else
            {
                userQuiz.addQuestion(getQuestion());
            }

            return true;
        }
        else
        {
            warning.setText("Please fill in all question fields and ensure no answers are the same.");
            return false;
        }
    }

    //Deletes the current question from the UserQuiz
    private void deleteQuestion()
    {
        userQuiz.deleteQuestion(questionIndex);
    }

    //Reverts UI elements to their default state
    private void refreshQuestionDisplay()
    {
        resetQuestionFields();
        warning.setText("");

        if (bPreviouslyCreatedQuestion)
        {
            fillFieldsFromQuestion(userQuiz.getQuestion(questionIndex));
            addQuestion.setText("Update Question");
        }
        else
        {
            addQuestion.setText("Add Question");
        }

        previousQuestion.setEnabled(questionIndex > 0);
        nextQuestion.setEnabled(bPreviouslyCreatedQuestion);
        deleteQuestion.setEnabled(bPreviouslyCreatedQuestion);
    }

    //Checks to see if the Quiz is named and has at least 1 question
    private boolean isQuizValid()
    {
        return !quizInput.getText().toString().trim().equals("") && userQuiz.getNumberOfQuestions() > 0;
    }

    //Returns to the previous activity (EditUserQuizzes)
    private void returnToEditUserQuizzes()
    {
        startActivity(new Intent(this, EditUserQuizzes.class));
    }

    private void saveQuiz()
    {
        userQuiz.setQuizName(quizInput.getText().toString());

        ArrayList<UserQuiz> savedQuizzes = loadQuizzes();
        savedQuizzes.add(userQuiz);

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(savedQuizzes);
        editor.putString("quizzes", json);
        editor.apply();
    }

    private void updateQuiz()
    {
        userQuiz.setQuizName(quizInput.getText().toString());

        ArrayList<UserQuiz> savedQuizzes = loadQuizzes();
        savedQuizzes.set(getUpdateQuizIndex(), userQuiz);

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(savedQuizzes);
        editor.putString("quizzes", json);
        editor.apply();
    }

    private ArrayList<UserQuiz> loadQuizzes()
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

        return savedQuizzes;
    }

    private boolean updatingQuiz()
    {
        return getIntent().getExtras().getBoolean("updateQuiz");
    }

    private int getUpdateQuizIndex()
    {
        return getIntent().getExtras().getInt("quizIndex");
    }

    private void setupUpdateQuiz()
    {
        finishQuizCreation.setText("Update Quiz");

        ArrayList<UserQuiz> savedQuizzes = loadQuizzes();
        userQuiz = savedQuizzes.get(getUpdateQuizIndex());

        quizInput.setText(userQuiz.getQuizName());

        bPreviouslyCreatedQuestion = true;
        refreshQuestionDisplay();
    }
}
