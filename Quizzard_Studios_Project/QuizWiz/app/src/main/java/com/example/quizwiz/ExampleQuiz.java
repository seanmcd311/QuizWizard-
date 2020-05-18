package com.example.quizwiz;

//Class to pass to the Adapter for the RecyclerView
public class ExampleQuiz {
    private String quizName;
    private int quizNumber;
    private int questionNumbers;

    public ExampleQuiz(String quizName, int quizNumber, int questionNumbers)
    {
        this.quizName = quizName;
        this.quizNumber = quizNumber;
        this.questionNumbers = questionNumbers;
    }

    public String getQuizName()
    {
        return quizName;
    }

    public int getQuizNumber()
    {
        return quizNumber;
    }

    public int getQuestionNumbers()
    {
        return questionNumbers;
    }
}
