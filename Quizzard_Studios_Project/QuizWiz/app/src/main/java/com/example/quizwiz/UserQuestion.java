package com.example.quizwiz;

public class UserQuestion
{
    private String question;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String correctAnswer;

    UserQuestion(String question, String choice1, String choice2, String choice3, String choice4, String correctAnswer)
    {
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion()
    {
        return question;
    }

    public String getAnswer(int choice)
    {
        if (choice == 1)
        {
            return choice1;
        }
        else if (choice == 2)
        {
            return choice2;
        }
        else if (choice == 3)
        {
            return choice3;
        }
        else
        {
            return choice4;
        }
    }

    public String getCorrectAnswer()
    {
        return correctAnswer;
    }
}
