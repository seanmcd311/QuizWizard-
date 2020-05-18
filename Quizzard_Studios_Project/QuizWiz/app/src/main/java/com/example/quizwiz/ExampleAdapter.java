package com.example.quizwiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    RecyclerView recyclerView;

    private ArrayList<ExampleQuiz> exampleQuizzes;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder
    {
        private final Context context;

        public TextView quizName;
        public TextView quizNumber;
        public TextView questionNumber;

        public ImageButton editButton;
        public ImageButton deleteButton;
        public Button startQuizButton;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            quizName = itemView.findViewById(R.id.CardQuizName);
            quizNumber = itemView.findViewById(R.id.CardQuizNumber);
            questionNumber = itemView.findViewById(R.id.CardQuestionNumbers);

            context = itemView.getContext();

            editButton = itemView.findViewById(R.id.editQuizButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    openUpdateQuizActivity();
                }
            });

            deleteButton = itemView.findViewById(R.id.deleteQuizButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    deleteQuiz();
                    remove();
                }
            });

            startQuizButton = itemView.findViewById(R.id.startQuiz);
            startQuizButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    startQuiz();
                }
            });
        }

        //Opens the CreateQuiz activity, but with parameters to modify quizzes
        private void openUpdateQuizActivity()
        {
            Intent intent = new Intent(context, CreateQuiz.class);
            intent.putExtra("updateQuiz", true);
            intent.putExtra("quizIndex", getAdapterPosition());
            context.startActivity(intent);
        }

        //Starts the quiz by opening the UserQuizDifficulty activity
        private void startQuiz()
        {
            Intent intent = new Intent(context, UserQuizDifficulty.class);
            intent.putExtra("quizIndex", getAdapterPosition());
            context.startActivity(intent);
        }

        //Deletes the saved quiz and removes it from the RecyclerView
        private void deleteQuiz()
        {
            ArrayList<UserQuiz> savedQuizzes = loadSavedQuizzes();
            savedQuizzes.remove(getAdapterPosition());
            saveQuizzes(savedQuizzes);
        }

        //Loads all the saved quizzes
        private ArrayList<UserQuiz> loadSavedQuizzes()
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", context.MODE_PRIVATE);
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

        //Save the quizzes
        private void saveQuizzes(ArrayList<UserQuiz> quizzesToSave)
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(quizzesToSave);
            editor.putString("quizzes", json);
            editor.apply();
        }

        //Removes the Quiz Card from the RecyclerView
        private void remove()
        {
            ((EditUserQuizzes) context).removeItem(getAdapterPosition());
        }
    }

    public ExampleAdapter(ArrayList<ExampleQuiz> exampleList)
    {
        exampleQuizzes = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_quiz, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position)
    {
        ExampleQuiz currentItem = exampleQuizzes.get(position);

        holder.quizName.setText(currentItem.getQuizName());
        holder.quizNumber.setText(currentItem.getQuizNumber() + ".");

        int iNumQuestions = currentItem.getQuestionNumbers();
        String sNumQuestions = iNumQuestions + " Question";
        if (iNumQuestions > 1)
        {
            sNumQuestions += "s";
        }

        holder.questionNumber.setText(sNumQuestions);
    }

    @Override
    public int getItemCount()
    {
        return exampleQuizzes.size();
    }
}
