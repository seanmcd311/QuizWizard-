package com.example.quizwiz;

import java.util.ArrayList;

public class UserQuiz
{
    private String quizName;
    private ArrayList<UserQuestion> questions;

    UserQuiz()
    {
        quizName = "";
        questions = new ArrayList<UserQuestion>();
    }

    UserQuiz(String quizName, ArrayList<UserQuestion> questions)
    {
        this.quizName = quizName;
        this.questions = questions;
    }

    public String getQuizName()
    {
        return quizName;
    }

    public void setQuizName(String quizName)
    {
        this.quizName = quizName;
    }

    //Adds a question to the end of the ArrayList
    public void addQuestion(UserQuestion question)
    {
        questions.add(question);
    }

    public UserQuestion getQuestion(int index)
    {
        return questions.get(index);
    }

    //Replaces a question with a new question
    public void updateQuestion(int index, UserQuestion newQuestion)
    {
        questions.set(index, newQuestion);
    }

    public int getNumberOfQuestions()
    {
        return questions.size();
    }

    //Removes a question
    public void deleteQuestion(int index)
    {
        questions.remove(index);
    }
}
